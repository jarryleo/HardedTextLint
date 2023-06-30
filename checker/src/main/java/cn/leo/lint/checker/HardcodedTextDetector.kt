package cn.leo.lint.checker

import com.android.tools.lint.client.api.ResourceRepositoryScope
import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UElement
import org.jetbrains.uast.ULiteralExpression
import java.util.regex.Pattern


class HardcodedTextDetector : Detector(), Detector.UastScanner {

    companion object {
        // 注册该检查器
        val ISSUE = Issue.create(
            "HardcodedText_Replace_Strings",
            "Avoid using hardcoded texts",
            "Hardcoded texts make it difficult to maintain and localize the app.",
            Category.CORRECTNESS, 5, Severity.WARNING,
            Implementation(HardcodedTextDetector::class.java, Scope.JAVA_FILE_SCOPE)
        )
    }

    override fun getApplicableUastTypes(): List<Class<out UElement?>> {
        return listOf(ULiteralExpression::class.java)
    }

    override fun createUastHandler(context: JavaContext): UElementHandler {
        return object : UElementHandler() {
            override fun visitLiteralExpression(node: ULiteralExpression) {
                handleExpression(context, node)
            }
        }
    }

    private fun handleExpression(context: JavaContext, expression: ULiteralExpression) {
        val value: String? = expression.value as? String
        val regex = "[\\u4e00-\\u9fa5]" //检查是否包含中文，包含中文则认为是中文硬编码
        val pattern = Pattern.compile(regex)
        if (value != null && pattern.matcher(value).find()) {
            val resourceName =
                findResourceName(context, value) ?: ("string_" + value.hashCode().toString())
            val suggestedValue = "ResUtils.getString(R.string.$resourceName)"
            val lintFix = fix()
                .name("Replace with $suggestedValue")
                .replace()
                .text(expression.asSourceString())
                .with(suggestedValue)
                .range(context.getLocation(expression.uastParent ?: expression))
                .autoFix()
                .build()
            context.report(
                ISSUE,
                expression,
                context.getLocation(expression),
                "Avoid using hardcoded texts",
                lintFix
            )
        }
    }

    private fun findResourceName(context: JavaContext, value: String): String? {
        val client = context.client
        val resources =
            client.getResources(context.project, ResourceRepositoryScope.LOCAL_DEPENDENCIES)
        val stringItems = resources.allResources
        return stringItems.find {
            it.resourceValue?.value?.trim() == value.trim()
        }?.name
    }

}
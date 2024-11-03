package com.emamagic;

import com.intellij.codeInspection.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.ResourceBundle;

public class ConfigAnnotationInspection extends LocalInspectionTool {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages.InspectionBundle");
    private static final String MESSAGE_INTERFACE = bundle.getString("inspection.configAnnotation.interface.description");
    private static final String MESSAGE_CLASS_ONLY = bundle.getString("inspection.configAnnotation.classOnly.description");

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new PsiElementVisitor() {
            @Override
            public void visitElement(@NotNull PsiElement element) {
                super.visitElement(element);
                if (element instanceof PsiClass) {
                    visitClass((PsiClass) element);
                }
            }

            public void visitClass(@NotNull PsiClass aClass) {
                if (aClass.hasAnnotation("com.emamagic.annotation.Config")) {

                    if (aClass.isInterface() || aClass.isEnum()) {
                        holder.registerProblem(
                                Objects.requireNonNull(aClass.getNameIdentifier()),
                                MESSAGE_CLASS_ONLY,
                                ProblemHighlightType.GENERIC_ERROR,
                                new QuickFix.RemoveAnnotationQuickFix(
                                        "com.emamagic.annotation.Config",
                                        "Remove @Config annotation"
                                )
                        );
                        return;
                    }

                    boolean implementsIConfig = false;
                    for (PsiClass anInterface : aClass.getInterfaces()) {
                        if ("com.emamagic.conf.IConfig".equals(anInterface.getQualifiedName())) {
                            implementsIConfig = true;
                            break;
                        }
                    }

                    if (!implementsIConfig) {
                        holder.registerProblem(
                                Objects.requireNonNull(aClass.getNameIdentifier()),
                                MESSAGE_INTERFACE,
                                ProblemHighlightType.GENERIC_ERROR,
                                new QuickFix.ImplementIConfigQuickFix()
                        );
                    }
                }
            }
        };
    }

}

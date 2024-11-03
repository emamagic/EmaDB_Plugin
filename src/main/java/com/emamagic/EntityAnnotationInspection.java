package com.emamagic;

import com.intellij.codeInspection.*;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.ResourceBundle;

public class EntityAnnotationInspection extends LocalInspectionTool {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages.InspectionBundle");
    private static final String MESSAGE_SINGLE_ID = bundle.getString("inspection.entityAnnotation.singleId.description");
    private static final String MESSAGE_CLASS_ONLY = bundle.getString("inspection.entityAnnotation.classOnly.description");
    private static final String MESSAGE_NO_ARG_CONSTRUCTOR = bundle.getString("inspection.entityAnnotation.noArgConstructor.description");

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
                if (aClass.hasAnnotation("com.emamagic.annotation.Entity")) {

                    if (aClass.isInterface() || aClass.isEnum()) {
                        holder.registerProblem(
                                Objects.requireNonNull(aClass.getNameIdentifier()),
                                MESSAGE_CLASS_ONLY,
                                ProblemHighlightType.GENERIC_ERROR,
                                new QuickFix.RemoveAnnotationQuickFix(
                                        "com.emamagic.annotation.Entity",
                                        "Remove @Entity annotation"
                                )
                        );
                        return;
                    }

                    if (!validateSingleIdField(aClass, holder)) return;
                    validateNoArgsConstructor(aClass, holder);
                }
            }

            private boolean validateSingleIdField(PsiClass aClass, ProblemsHolder holder) {
                PsiField[] fields = aClass.getFields();
                int idCount = 0;
                for (PsiField field : fields) {
                    if (field.hasAnnotation("com.emamagic.annotation.Id")) {
                        idCount++;
                    }
                }

                if (idCount != 1) {
                    holder.registerProblem(
                            Objects.requireNonNull(aClass.getNameIdentifier()),
                            MESSAGE_SINGLE_ID,
                            ProblemHighlightType.GENERIC_ERROR
                    );
                    return false;
                }
                return true;
            }


            private void validateNoArgsConstructor(PsiClass aClass, ProblemsHolder holder) {
                PsiMethod[] constructors = aClass.getConstructors();
                boolean hasPublicNoArgConstructor = false;

                if (constructors.length == 0) {
                    return;
                }

                for (PsiMethod constructor : constructors) {
                    if (constructor.getParameterList().isEmpty()) {
                        if (constructor.hasModifierProperty(PsiModifier.PUBLIC)) {
                            hasPublicNoArgConstructor = true;
                            break;
                        } else {
                            holder.registerProblem(
                                    Objects.requireNonNull(aClass.getNameIdentifier()),
                                    MESSAGE_NO_ARG_CONSTRUCTOR,
                                    ProblemHighlightType.GENERIC_ERROR
                            );
                            return;
                        }
                    }
                }

                if (!hasPublicNoArgConstructor) {
                    holder.registerProblem(
                            Objects.requireNonNull(aClass.getNameIdentifier()),
                            MESSAGE_NO_ARG_CONSTRUCTOR,
                            ProblemHighlightType.GENERIC_ERROR
                    );
                }
            }

        };
    }

}

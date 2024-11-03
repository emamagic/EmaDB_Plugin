package com.emamagic;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.ResourceBundle;

public class IdAnnotationInspection extends LocalInspectionTool {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("messages.InspectionBundle");
    private static final String MESSAGE_INVALID_ID_TYPE_POSTGRESQL = bundle.getString("inspection.idAnnotation.invalidIdTypePostgres.description");
    private static final String MESSAGE_INVALID_ID_TYPE_MONGODB = bundle.getString("inspection.idAnnotation.invalidIdTypeMongo.description");

    @Override
    public @NotNull PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new PsiElementVisitor() {

            @Override
            public void visitElement(@NotNull PsiElement element) {
                super.visitElement(element);
                if (element instanceof PsiField) {
                    visitField((PsiField) element, holder);
                }
            }

            private void visitField(@NotNull PsiField field, @NotNull ProblemsHolder holder) {
                if (field.hasAnnotation("com.emamagic.annotation.Id")) {
                    PsiClass aClass = field.getContainingClass();
                    if (aClass != null) {
                        validateIdFieldType(field, aClass, holder);
                    }
                }
            }

            private void validateIdFieldType(PsiField field, PsiClass aClass, ProblemsHolder holder) {
                PsiAnnotation entityAnnotation = aClass.getAnnotation("com.emamagic.annotation.Entity");
                if (entityAnnotation != null) {
                    String dbType = getDbTypeFromEntityAnnotation(entityAnnotation);
                    if (dbType != null) {
                        if ("DB.POSTGRESQL".equals(dbType) && !isValidPostgresIdType(field)) {
                            holder.registerProblem(
                                    field.getNameIdentifier(),
                                    MESSAGE_INVALID_ID_TYPE_POSTGRESQL,
                                    ProblemHighlightType.GENERIC_ERROR
                            );
                        } else if ("DB.MONGODB".equals(dbType) && !isValidMongoIdType(field)) {
                            holder.registerProblem(
                                    field.getNameIdentifier(),
                                    MESSAGE_INVALID_ID_TYPE_MONGODB,
                                    ProblemHighlightType.GENERIC_ERROR
                            );
                        }
                    }
                }
            }

            private String getDbTypeFromEntityAnnotation(PsiAnnotation entityAnnotation) {
                PsiAnnotationMemberValue dbTypeValue = entityAnnotation.findAttributeValue("db");
                return dbTypeValue != null ? dbTypeValue.getText() : null;
            }

            private boolean isValidPostgresIdType(PsiField field) {
                PsiType fieldType = field.getType();
                return fieldType.equalsToText("java.lang.Integer") || fieldType.equalsToText("java.lang.String");
            }

            private boolean isValidMongoIdType(PsiField field) {
                return field.getType().equalsToText("org.bson.types.ObjectId");
            }

        };
    }
}

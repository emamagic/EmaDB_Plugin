package com.emamagic;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

public class QuickFix {


    public static class RemoveAnnotationQuickFix implements LocalQuickFix {
        private final String annotationQualifiedName;
        private final String message;

        public RemoveAnnotationQuickFix(String annotationQualifiedName, String message) {
            this.annotationQualifiedName = annotationQualifiedName;
            this.message = message;
        }
        @NotNull
        @Override
        public String getFamilyName() {
            return message;
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiClass psiClass = (PsiClass) descriptor.getPsiElement().getParent();
            if (psiClass != null) {
                PsiModifierList modifierList = psiClass.getModifierList();
                if (modifierList != null) {
                    PsiAnnotation configAnnotation = modifierList.findAnnotation(annotationQualifiedName);
                    if (configAnnotation != null) {
                        configAnnotation.delete();
                    }
                }
            }
        }
    }

    public static class ImplementIConfigQuickFix implements LocalQuickFix {

        @NotNull
        @Override
        public String getFamilyName() {
            return "Implement IConfig interface";
        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiClass psiClass = (PsiClass) descriptor.getPsiElement().getParent();
            if (psiClass != null) {
                PsiElementFactory factory = PsiElementFactory.getInstance(project);
                PsiReferenceList referenceList = psiClass.getImplementsList();
                if (referenceList != null) {
                    referenceList.add(factory.createReferenceElementByFQClassName("com.emamagic.conf.IConfig", psiClass.getResolveScope()));
                }
            }
        }
    }


    public static class AddNoArgsConstructorQuickFix implements LocalQuickFix {

        @NotNull
        @Override
        public String getFamilyName() {
            return "Add no-arg constructor";

        }

        @Override
        public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
            PsiClass psiClass = (PsiClass) descriptor.getPsiElement().getParent();
            if (psiClass != null) {
                PsiElementFactory factory = JavaPsiFacade.getInstance(project).getElementFactory();
                PsiMethod noArgConstructor = factory.createConstructor();
                psiClass.add(noArgConstructor);
            }
        }
    }
}

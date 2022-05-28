package ru.kivilev;

import ru.kivilev.annotations.Log;

import java.lang.annotation.Annotation;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class Agent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain");
      //  processLogAnnotation(inst);
    }
/*
    private static void processLogAnnotation(Instrumentation inst) {
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader,
                                    String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {

                if (className.contains("ru/kivilev")) {
                    System.out.println(className);
                    if (isAnnotationExists(className, Log.class)) {
                        System.out.println("Log");
                    }
                }
                return classfileBuffer;
            }
        });
    }

    private static byte[] changeMethod(byte[] originalClass) {
        var cr = new ClassReader(originalClass);
        var cw = new ClassWriter(cr, ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM7, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                var methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
                if (name.equals("summator")) {
                    return new ChangeMethodVisitor(methodVisitor, access, name, descriptor);
                } else {
                    return methodVisitor;
                }
            }
        };
        cr.accept(cv, Opcodes.ASM7);

        byte[] finalClass = cw.toByteArray();

        try (OutputStream fos = new FileOutputStream("changer.class")) {
            fos.write(finalClass);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return finalClass;
    }

    private static class ChangeMethodVisitor extends AdviceAdapter {
        ChangeMethodVisitor(MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(Opcodes.ASM7, methodVisitor, access, name, descriptor);
        }

        @Override
        public void visitInsn(final int opcode) {
            if (IADD == opcode) {
                System.out.println("replace IADD to ISUB");
                super.visitInsn(ISUB);
            } else {
                super.visitInsn(opcode);
            }
        }
    }*/


  /*  //@SneakyThrows


    private static String convertClassNameToNormalClassName(String className) {
        return className.replaceAll("/", ".");
    }*/
}

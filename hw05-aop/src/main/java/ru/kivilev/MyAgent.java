package ru.kivilev;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class MyAgent {
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("premain");
        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className,
                                    Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain,
                                    byte[] classfileBuffer) {
                if (className.contains("/ru/kivilev")) {
                    System.out.println(className);
                    var cr = new ClassReader(classfileBuffer);
                    return processClass(cr);
                }
                return classfileBuffer;
            }
        });
    }

    private static byte[] processClass(ClassReader cr) {
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM5, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (name.equals(originalMethodName)) {
                    return super.visitMethod(access, proxiedMethodName, descriptor, signature, exceptions);
                } else {
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }
        };
        return ;
    }
}

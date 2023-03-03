package net.minecraftforge.fml.common.asm.transformers;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;
import org.objectweb.asm.tree.ClassNode;

import java.io.IOException;

public class ASMUpper implements IClassTransformer {

    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (bytes == null) {
            return null;
        }

        if (bytes.length== 0) {
            return bytes;
        }

        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode,0);

        ClassReader classReader2 = null;

        String superClass = null;
        while (!(classReader.getSuperName() != null)) {
            try {
                classReader2 = new ClassReader(classReader.getSuperName());
            } catch (IOException ignored) {
            }
            superClass = classReader2.getSuperName();
        }
        if (superClass == null) return bytes;
        String className = classReader.getClassName();
        if (superClass.equals("org/objectweb/asm/ClassVisitor") || superClass.equals("org/objectweb/asm/MethodVisitor") || superClass.equals("org/objectweb/asm/FieldVisitor")) {
            classReader.accept(new MyClassVisitor(classWriter), 0);
            return classWriter.toByteArray();
        }
        return bytes;
    }

    class MyClassVisitor extends ClassVisitor {

        MethodVisitor mv;
        protected MyClassVisitor(ClassWriter classWriter) {
            super(Opcodes.ASM9, classWriter);
            mv = new MyMethodVisitor();
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            if (name.equals("<init>")) {
                return new MyMethodVisitor(Opcodes.ASM9, mv);
            }
            return super.visitMethod(access, name, descriptor, signature, exceptions);
        }
    }

    class MyMethodVisitor extends MethodVisitor {

        protected MyMethodVisitor(int api, MethodVisitor methodVisitor) {
            super(api,methodVisitor);
        }

        protected MyMethodVisitor() {
            super(Opcodes.ASM9);
        }
        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (opcode == Opcodes.INVOKESPECIAL) {
                if (desc.equals("(I)V") ||
                        desc.equals("(ILorg/objectweb/asm/ClassVisitor;)V") || desc.equals("(ILorg/objectweb/asm/MethodVisitor;)V") || desc.equals("(ILorg/objectweb/asm/FieldVisitor;)V") ||
                        desc.equals("(Lorg/objectweb/asm/ClassVisitor;)V") || desc.equals("(Lorg/objectweb/asm/MethodVisitor;)V") || desc.equals("(Lorg/objectweb/asm/FieldVisitor;)V")) {
                    visitInsn(Opcodes.POP);
                    visitInsn(Opcodes.POP);
                    visitInsn(Opcodes.POP);
                    visitVarInsn(Opcodes.ALOAD, 0);
                    visitLdcInsn(Opcodes.ASM9);
                    visitVarInsn(Opcodes.ILOAD, 1);
                    visitVarInsn(Opcodes.ALOAD, 2);
                }
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }
    }
}
package org.lwjgl.openal;

import org.lwjgl.MemoryUtil;

import java.nio.ByteBuffer;

public class ALC10 {

    static ALCcontext alcContext;

    public static final int ALC_FREQUENCY = org.lwjgl3.openal.ALC10.ALC_FREQUENCY;
    public static final int ALC_REFRESH = org.lwjgl3.openal.ALC10.ALC_REFRESH;
    public static final int ALC_SYNC = org.lwjgl3.openal.ALC10.ALC_SYNC;
    public static final int ALC_NO_ERROR = org.lwjgl3.openal.ALC10.ALC_NO_ERROR;
    public static final int ALC_DEFAULT_DEVICE_SPECIFIER = org.lwjgl3.openal.ALC10.ALC_DEFAULT_DEVICE_SPECIFIER;
    public static final int ALC_DEVICE_SPECIFIER = org.lwjgl3.openal.ALC10.ALC_DEVICE_SPECIFIER;

    public static int alcGetError(ALCdevice device) {
        if (device == null) {
            return org.lwjgl3.openal.ALC10.alcGetError(AL.alcDevice.device);
        }

        return org.lwjgl3.openal.ALC10.alcGetError(device.device);
    }

    public static java.lang.String alcGetString(ALCdevice device, int pname) {
        if (device == null) {
            return org.lwjgl3.openal.ALC10.alcGetString(AL.alcDevice.device, pname);
        }

        return org.lwjgl3.openal.ALC10.alcGetString(device.device, pname);
    }

    public static ALCdevice alcOpenDevice(String devicename) {
        long device_address = org.lwjgl3.openal.ALC10.alcOpenDevice(devicename);
        if(device_address != 0) {
            return new ALCdevice(device_address);
        }
        return null;
    }

    public static boolean alcIsExtensionPresent(ALCdevice device, java.lang.String extName) {
        if (device == null) {
            return org.lwjgl3.openal.ALC10.alcIsExtensionPresent(AL.alcDevice.device, extName);
        }

        return org.lwjgl3.openal.ALC10.alcIsExtensionPresent(device.device, extName);
    }

    public static ALCcontext alcCreateContext(ALCdevice device, java.nio.IntBuffer attrList) {
        long alContextHandle = org.lwjgl3.openal.ALC10.alcCreateContext(device.device, attrList);
        alcContext = new ALCcontext(alContextHandle);
        return alcContext;
    }

    public static void alcCloseDevice(ALCdevice alCdevice) {
        org.lwjgl3.openal.ALC10.alcCloseDevice(alCdevice.device);
    }

    public static ALCcontext alcGetCurrentContext() {
        return alcContext;
    }

    public static ALCdevice alcGetContextsDevice(ALCcontext context) {
        return AL.alcDevice;
    }

    public static void alcGetInteger(ALCdevice device, int pname, java.nio.IntBuffer integerdata) {
        org.lwjgl3.openal.ALC10.alcGetIntegerv(device.device, pname, integerdata);
    }
}

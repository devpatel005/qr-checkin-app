package com.QRcode.qrcheckin.Package;

public class ResourceNotFoundException extends Throwable {
    public ResourceNotFoundException(String qrCodeNotFound) {
        System.out.println("Something wrong cuh" + qrCodeNotFound);
    }
}

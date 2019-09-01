package demo.cloudinary.service;

import java.io.File;

import cloudinary.pojo.result.CloudinaryUploadResult;

public interface CloudinaryService {

	CloudinaryUploadResult upload(File f);

}

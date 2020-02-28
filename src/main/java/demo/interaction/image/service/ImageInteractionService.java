package demo.interaction.image.service;

import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;

public interface ImageInteractionService {

	UploadImageToCloudinaryResult uploadImageToCloudinary(UploadImageToCloudinaryDTO dto);

}

package demo.interaction.image;

import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.UploadImageToCloudinaryResult;

public interface ImageInteractionService {

	UploadImageToCloudinaryResult uploadImageToCloudinary(UploadImageToCloudinaryDTO dto);

}

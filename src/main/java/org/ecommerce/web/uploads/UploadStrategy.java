package org.ecommerce.web.uploads;

import org.ecommerce.web.models.upload.RequestUploadFile;
import org.ecommerce.web.models.upload.UploadFileInfo;
import org.ecommerce.web.uploads.exceptions.FileNotFoundException;
import org.ecommerce.web.uploads.exceptions.UploadFailException;

/**
 *
 * @author sergio
 */
public interface UploadStrategy<T, E extends RequestUploadFile> {
	T save(E fileinfo) throws UploadFailException;

	T append(E fileinfo, T id) throws FileNotFoundException, UploadFailException;

	T appendIfExists(E fileinfo, T id) throws UploadFailException;

	void delete(T id);

	UploadFileInfo get(T id) throws FileNotFoundException, UploadFailException;
}

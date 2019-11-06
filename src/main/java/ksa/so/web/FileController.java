package ksa.so.web;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ksa.so.domain.Branch;
import ksa.so.domain.Item;
import ksa.so.domain.LibraryItem;
import ksa.so.domain.MetaLanguage;
import ksa.so.payload.UploadFileResponse;
import ksa.so.repositories.BranchRepository;
import ksa.so.repositories.ItemLanguageRepository;
import ksa.so.repositories.ItemRepository;
import ksa.so.repositories.LibraryItemLanguageRepository;
import ksa.so.repositories.LibraryItemRepository;
import ksa.so.repositories.MetaLanguageRepository;
import ksa.so.repositories.MetaStatusRepository;
import ksa.so.service.FileStorageService;

@RestController
@RequestMapping("/api/file")

public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService fileStorageService;

	@Autowired
	ItemRepository itemRepository;

	@Autowired
	MetaLanguageRepository languageRepository;

	@Autowired
	BranchRepository branchRepository;

	@Autowired
	MetaStatusRepository statusRepository;

	@Autowired
	LibraryItemRepository libraryItemRepository;

	@Autowired
	ItemLanguageRepository itemLanguageRepository;

	@Autowired
	LibraryItemLanguageRepository libraryItemLanguageRepository;

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST, headers = { "content-type=multipart/mixed",
			"content-type=multipart/form-data" })
	public UploadFileResponse uploadFile(@RequestPart(value = "file") MultipartFile file,
			@RequestPart(value = "branch_id") String branch_id)
//    		@RequestParam("file") MultipartFile file)
	{
		// String fileName = fileStorageService.storeFile(file);

		File inputFile = fileStorageService.convert(file);
		// provide your path
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String uniqueID = java.util.UUID.randomUUID().toString() + "-" + timeStamp;
		String outputFilePath = ".//uploads//importCSV" + uniqueID + ".csv";
		File outputFile = new File(outputFilePath);

		// writing excel data to csv
		fileStorageService.xlsxToCSV(inputFile, outputFile, uniqueID, branch_id);

		// bulk load data to staging table
		itemRepository.bulkLoadData(outputFilePath);
		itemRepository.bulkImportData(uniqueID);
		// fuzzyMatchProducts(branch_id);
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();

		deleteFile(outputFilePath);
		return new UploadFileResponse("", "", file.getContentType(), file.getSize());
	}

	@RequestMapping(value = "/uploadMFile", method = RequestMethod.POST)
	public @ResponseBody String uploadFileHandler(@RequestParam("name") String name,
			@RequestParam("check") String check, @RequestParam("file") MultipartFile file) {

		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String rootPath = "";
				// Creating the directory to store file
				if (check == "S")
					rootPath = "C:\\inetpub\\wwwroot\\SmartCart\\Images\\Stores";
				else
					rootPath = "C:\\inetpub\\wwwroot\\SmartCart\\Images\\LibraryItemImages";
				File dir = new File(rootPath);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location=" + serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name + "<br />";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}

	// private void fuzzyMatchProducts(String branch_id) {
	@RequestMapping(value = "/srs_fil_002", method = RequestMethod.POST, consumes = "text/plain", produces = {
			"application/json; charset=UTF-8" })
	public String fuzzyMatchProducts(@RequestBody String param, HttpServletRequest req) throws Exception {
		JSONObject jsonParam = new JSONObject(param);
		JSONObject jsonData = jsonParam.getJSONObject("data");
		// JSONObject jsonUserInfo = jsonData.getJSONObject("userAppInfo");
		// JSONObject jsonCountryInfo = jsonUserInfo.getJSONObject("countryInfo");
		JSONObject jsonLanguageInfo = jsonData.getJSONObject("languageInfo");
		JSONObject jsonBranchInfo = jsonData.getJSONObject("branchInfo");

		MetaLanguage language = languageRepository.findByCode(jsonLanguageInfo.getString("code"));
		Branch branch = branchRepository.findOne(jsonBranchInfo.getLong("id"));

		float score = 0;
		float finalScore = 0;
		// List<Item> itemList =
		// itemRepository.findByBranch(branchRepository.findOne(jsonBranchInfo.getLong("id")));
		List<Object[]> itemList = itemLanguageRepository.findByBranchAndLanguage(branch, language);
		// List<LibraryItem> libraryItemList = libraryItemRepository.findAll();
		List<Object[]> libraryItemList = libraryItemLanguageRepository.findByLanguage(language);
		for (int i = 0; i < itemList.size(); i++) {
//    		String str1 = itemList.get(i).getItemLanguageList().get(0).getTitle();
			String str1 = (String) itemList.get(i)[1];
			LibraryItem libraryItem = new LibraryItem();
			Item item = new Item();
			finalScore = 0;
			for (int j = 0; j < libraryItemList.size(); j++) {
//        		String str2 = libraryItemList.get(j).getItemLanguageList().get(0).getTitle();
				String str2 = (String) libraryItemList.get(j)[1];
				score = fileStorageService.LevenshteinDistanceCaseInsensitive(str1, str2);
				if (score > finalScore) {
					finalScore = score;
					libraryItem = libraryItemRepository.findOne((Long) libraryItemList.get(j)[0]);
				}
			}
			if (finalScore > 0.9) {
				// item = itemList.get(i).getItem();
				item = itemRepository.findOne((Long) itemList.get(i)[0]);
				item.setLibraryItem(libraryItem);
				itemRepository.save(item);
			}
		}
		logger.info("fuzzy matching done");

		JSONObject json = new JSONObject();
		// add server timestamp
		json.put("Server Timestamp", statusRepository.getCurrentTime());

		return json.toString();
	}

	private void deleteFile(String outputFilePath) {
		try {
			Files.deleteIfExists(Paths.get(outputFilePath));
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
		}

		System.out.println("Deletion successful.");

	}

	@PostMapping("/uploadMultipleFiles")
	public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		return Arrays.asList(files).stream().map(file -> uploadFile(file, null)).collect(Collectors.toList());
	}

	/*
	 * @GetMapping("/downloadFile/{fileName:.+}") public ResponseEntity<Resource>
	 * downloadFile(@PathVariable String fileName, HttpServletRequest request) { //
	 * Load file as Resource Resource resource =
	 * fileStorageService.loadFileAsResource(fileName);
	 *
	 * // Try to determine file's content type String contentType = null; try {
	 * contentType =
	 * request.getServletContext().getMimeType(resource.getFile().getAbsolutePath())
	 * ; } catch (IOException ex) { logger.info("Could not determine file type."); }
	 *
	 * // Fallback to the default content type if type could not be determined if
	 * (contentType == null) { contentType = "application/octet-stream"; }
	 *
	 * return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
	 * .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
	 * resource.getFilename() + "\"") .body(resource); }
	 */
}

package com.PizzaZone.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.PizzaZone.dao.ItemDao;
import com.PizzaZone.dao.ItemImageDao;
import com.PizzaZone.dao.ToppingDao;
import com.PizzaZone.dao.ToppingImageDao;
import com.PizzaZone.dto.ApiResponse;
import com.PizzaZone.entities.Item;
import com.PizzaZone.entities.ItemImage;
import com.PizzaZone.entities.ToppingImages;
import com.PizzaZone.entities.Toppings;
import com.app.custom_exceptions.ResourceNotFoundException;

@Service
@Transactional
public class ImageHandlerService {

	@Value("${pizza.upload.folder}")
	private String pizzaImageFolder;
	@Value("${topping.upload.folder}")
	private String toppingImageFolder;
	
	@Autowired 
	private ItemDao itemDao;
	@Autowired
	private ItemImageDao itemImgDao;
	
	@Autowired
	private ToppingDao toppingDao;
	@Autowired
	private ToppingImageDao toppingImgDao;
	

	@PostConstruct
	public void myInit() {
		System.out.println("in myInit " + pizzaImageFolder);
		// chk of folder exists --o.w create one!
		File pizzaImagePath = new File(pizzaImageFolder);
		File toppingImagePath=new File(toppingImageFolder);
		if (!pizzaImagePath.exists()) {
			pizzaImagePath.mkdirs();
		}
		if(!toppingImagePath.exists()) {
			toppingImagePath.mkdirs();
		}
		else {
			System.out.println("folders alrdy exists....");
		}

	}

	public ApiResponse uploadPizzaImage(int Id, MultipartFile imageFile) throws IOException {
		Item pizza = itemDao.findByItemid(Id)
				.orElseThrow(() -> new ResourceNotFoundException("InValid pizza Id !!!!!"));
		String targetPath = pizzaImageFolder + File.separator + "pizza" + pizza.getItemid()+ "."
				+ imageFile.getOriginalFilename().split("\\.")[1];
		System.out.println("Pizza Image Path : " + targetPath);
		ItemImage img=new ItemImage();
		Files.copy(imageFile.getInputStream(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
		img.setData(targetPath);
		img.setItem(pizza);
		itemImgDao.save(img);
		
		return new ApiResponse("Pizza Image Uploaded Sucessfully !!!");
	}
	
	public ApiResponse uploadToppingImage(int Id, MultipartFile imageFile) throws IOException {
		Toppings topping= toppingDao.findByToppingId(Id)
				.orElseThrow(() -> new ResourceNotFoundException("InValid topping Id !!!!!"));
		String targetPath = toppingImageFolder + File.separator + "topping" + topping.getToppingId()+ "."
				+ imageFile.getOriginalFilename().split("\\.")[1];
		System.out.println("Topping Image Path : " + targetPath);
		ToppingImages img=new ToppingImages();
		Files.copy(imageFile.getInputStream(), Paths.get(targetPath), StandardCopyOption.REPLACE_EXISTING);
		img.setData(targetPath);
		img.setToppings(topping);
		toppingImgDao.save(img);
		
		return new ApiResponse("Pizza Image Uploaded Sucessfully !!!");
	}
	
	public byte[] getPizzaImage(int itemId) throws IOException {
		
		ItemImage pizza = itemImgDao.findByItemId(itemId);
		String path = pizza.getData();
		if (path == null) {
			throw new ResourceNotFoundException("Image does Not Exists !!!!");
		}
		return Files.readAllBytes(Paths.get(path));
	}
	
	public byte[] getToppingImage(int toppingId) throws IOException {
		ToppingImages toppingimg = toppingImgDao.findById(toppingId)
				.orElseThrow(() -> new ResourceNotFoundException("InValid topping Id !!!!!"));
		String path = toppingimg.getData();
		if (path == null) {
			throw new ResourceNotFoundException("Image does Not Exists !!!!");
		}
		return Files.readAllBytes(Paths.get(path));
	}

}

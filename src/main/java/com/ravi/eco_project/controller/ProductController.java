package com.ravi.eco_project.controller;

import com.ravi.eco_project.model.Product;
import com.ravi.eco_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService service;

//    @RequestMapping("/")
//    public String greet(){
//        return "Yah worked";
//    }

    @RequestMapping("/products")
    public ResponseEntity<List<Product>> getAllProduct(){
        return new ResponseEntity<>(service.getAllProduct(), HttpStatus.OK);
    }
    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product product = service.getProductById(id);
        if(product != null){
            return new ResponseEntity<>(product, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(product, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @GetMapping("/product/{productId}/image")
   public ResponseEntity<byte[]> getImageById(@PathVariable int productId){
        Product product = service.getProductById(productId);
        byte[] imageFile = product.getImageData();
        return  ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
   }
   @PutMapping("/product/{id}")
    public ResponseEntity<String > updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile) throws IOException {
        Product product1 = service.updateProduct(id, product, imageFile);
       return new ResponseEntity<>("Update Thank you", HttpStatus.OK);
   }
   @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        Product product1 = service.getProductById(id);
        if(product1 != null){
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted Thank you", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("something went wrong", HttpStatus.BAD_REQUEST);
        }
   }
   @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        List<Product> products = service.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
   }


}

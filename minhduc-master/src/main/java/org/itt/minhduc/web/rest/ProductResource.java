package org.itt.minhduc.web.rest;

import com.codahale.metrics.annotation.Timed;

import org.itt.minhduc.service.FileStorageService;
import org.itt.minhduc.service.ProductService;
import org.itt.minhduc.service.ProductServiceCustom;
import org.itt.minhduc.web.rest.errors.BadRequestAlertException;
import org.itt.minhduc.web.rest.util.HeaderUtil;
import org.itt.minhduc.web.rest.util.PaginationUtil;
import org.itt.minhduc.service.dto.FileAtachmentDTO;
import org.itt.minhduc.service.dto.ProductDTO;
import org.itt.minhduc.service.mapper.FileAtachmentMapper;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

/**
 * REST controller for managing Product.
 */
@RestController
@RequestMapping("/api")
public class ProductResource {

    private final Logger log = LoggerFactory.getLogger(ProductResource.class);

    private static final String ENTITY_NAME = "product";

    private ProductService productService;
    
    private ProductServiceCustom productServiceCustom;
    
    @Autowired
    private FileStorageService fileStorageService;
    
    @Autowired
    private FileAtachmentMapper fileAtachmentMapper;

    public ProductResource(ProductService productService ,ProductServiceCustom productServiceCustom) {
    	this.productService = productService;
    	this.productServiceCustom = productServiceCustom;
    }

    /**
     * POST  /products : Create a new product.
     *
     * @param productDTO the productDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new productDTO, or with status 400 (Bad Request) if the product has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/products")
    @Timed
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to save Product : {}", productDTO);
        if (productDTO.getId() != null) {
            throw new BadRequestAlertException("A new product cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.created(new URI("/api/products/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /products : Updates an existing product.
     *
     * @param productDTO the productDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated productDTO,
     * or with status 400 (Bad Request) if the productDTO is not valid,
     * or with status 500 (Internal Server Error) if the productDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/products")
    @Timed
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) throws URISyntaxException {
        log.debug("REST request to update Product : {}", productDTO);
        if (productDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProductDTO result = productService.save(productDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, productDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /products : get all the products.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of products in body
     */
    @GetMapping("/products")
    @Timed
    public ResponseEntity<List<ProductDTO>> getAllProducts(Pageable pageable) {
        log.debug("REST request to get a page of Products");
        Page<ProductDTO> page = productService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/products");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /products/:id : get the "id" product.
     *
     * @param id the id of the productDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the productDTO, or with status 404 (Not Found)
     */
    @GetMapping("/products/{id}")
    @Timed
    public ResponseEntity<ProductDTO> getProduct(@PathVariable String id) {
        log.debug("REST request to get Product : {}", id);
        Optional<ProductDTO> productDTO = productService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productDTO);
    }

    /**
     * DELETE  /products/:id : delete the "id" product.
     *
     * @param id the id of the productDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/products/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        log.debug("REST request to delete Product : {}", id);
        productService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
    
    
    @SuppressWarnings({ "deprecation" })
	@GetMapping("/products/getAll")
    @Timed
    public StandardResponse<List<ProductDTO>> getAllProduct(
    		@RequestParam(value = "size", required = false) Integer size,
    		@RequestParam(value = "page", required = false) Integer page,
    		@RequestParam(value = "category_id", required = true) String id) {
    	
    	StandardResponse<List<ProductDTO>> response = new StandardResponse<List<ProductDTO>>();
    	try {
    		if(page == null) {
    			page = 0;
    		}
    		if(size == null) {
    			size = 100000;
    		}
    		Pageable pageable = new PageRequest(page, size);
            Page<ProductDTO> pages = productServiceCustom.getPage(id,pageable);  
            response.setCode(200);
            response.setMessage("success full");
            response.setValue(pages.getContent());
            response.setTotalElements(pages.getTotalElements());
            response.setTotalPages(pages.getTotalPages());
		} catch (Exception e) {
			response.setCode(400);
            response.setMessage(e.getMessage());
		}
		 	
    	return response;
    }
    

  
  @PostMapping("/product/upload")
  public StandardResponse<FileAtachmentDTO> uploadFile(@RequestParam(value ="file") MultipartFile file) {
	  StandardResponse<FileAtachmentDTO> response = new StandardResponse<>();
	  try {
		  FileAtachmentDTO fileAtachmentDTO = fileStorageService.storeFile(file);
//		  String fileName = fileStorageService.storeFile(file);
//	      String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//	              .path("/downloadFile/")
//	              .path(fileName)
//	              .toUriString();
//	      FileAtachmentDTO fileAtachmentDTO = new FileAtachmentDTO();
//	      fileAtachmentDTO.setFileName(fileName);
//	      fileAtachmentDTO.setFileType(file.getContentType());
//	      fileAtachmentDTO.setSize(file.getSize());
//	      fileAtachmentDTO.setFileDownloadUri(fileDownloadUri);
	      response.setCode(200);
	      response.setMessage("success");
	      response.setValue(fileAtachmentDTO);
	  } catch (Exception e) {
		  response.setCode(400);
	      response.setMessage("error");
		  
	  }
     
  
//      return new UploadFileResponse(fileName, fileDownloadUri,
//              file.getContentType(), file.getSize());
	  return response;
  }

//  @PostMapping("/uploadMultipleFiles")
//  public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//      return Arrays.asList(files)
//              .stream()
//              .map(file -> uploadFile(file))
//              .collect(Collectors.toList());
//  }
//
  @GetMapping("/downloadFile/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
      // Load file as Resource
      Resource resource = fileStorageService.loadFileAsResource(fileName);

      // Try to determine file's content type
      String contentType = null;
      try {
          contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
      } catch (IOException ex) {
          log.info("Could not determine file type.");
      }

      // Fallback to the default content type if type could not be determined
      if(contentType == null) {
          contentType = "application/octet-stream";
      }

      return ResponseEntity.ok()
              .contentType(MediaType.parseMediaType(contentType))
              .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
              .body(resource);
  }
}

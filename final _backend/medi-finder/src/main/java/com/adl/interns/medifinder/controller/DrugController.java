package com.adl.interns.medifinder.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.adl.interns.medifinder.entity.User;
import com.adl.interns.medifinder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adl.interns.medifinder.exception.ResourceNotFoundException;
import com.adl.interns.medifinder.entity.Drug;
import com.adl.interns.medifinder.repository.DrugRepository;
import com.adl.interns.medifinder.service.DrugServices;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DrugController {

  	private UserService userService;

	private DrugRepository drugRepository;

	private DrugServices drugServices;


	@Autowired
	public DrugController(DrugServices drugServices, DrugRepository drugRepository
			,UserService userService) {
		this.drugServices = drugServices;
		this.drugRepository = drugRepository;
		this.userService=userService;
	}
	


	@GetMapping("/load")
	  public List<Drug> getAllDrugs(Principal principal){
		String name=principal.getName();
		User user = userService.findCustomerByEmail(name);
	  	return drugRepository.findAllByUser(user);
	  }

//	  @GetMapping("/similar")
//	  public List<Drug> getSimilarProduct(String name){
//		  System.out.println("called"+name);
//
//		  Drug drug=drugRepository.findByDrugName(name);
//		if(drug!=null){
//			return drugRepository.findAllByGeneticNameAndAvailability(drug.getGeneticName(),true);
//		} else{
//			System.out.println("not found drug");
//			return null;
//		}
//	  }

	@PostMapping("/drugs")
	public String createDrug(Principal principal, @RequestBody Drug drug) {
		String name=principal.getName();
		User user = userService.findCustomerByEmail(name);
		if(drugRepository.findByDrugNameAndGeneticNameAndUser(drug.getDrugName()
				,drug.getGeneticName(),user)==null){
			drug.setUser(user);
			drugServices.createDrug(name, drug);
			return "Successful";
		}
		return "Already exist";
	}


	
	/*
	 * // get all drugs
	 * 
	 * @GetMapping("/drugs") public List<Drug> getAllDrugs(){ return
	 * drugRepository.findAll(); }
	 * 
	 * // create drug rest api
	 * 
	 * @PostMapping("/drugs") public Drug createDrug(@RequestBody Drug drug) {
	 * return drugRepository.save(drug); }
	 */
	
	// get drug by id rest api
	@GetMapping("/drugs/{id}")
	public ResponseEntity<Drug> getDrugById(@PathVariable Long id) {
		Drug drug = drugRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Drug not exist with id :" + id));
		return ResponseEntity.ok(drug);
	}
		
		// update drug rest api

	@PutMapping("/drugs/{id}")
	public ResponseEntity<Drug> updateDrug(Principal principal, @PathVariable Long id, @RequestBody Drug drugDetails){
		Drug drug = drugRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Drug not exist with id :" + id));

		drug.setDrugName(drugDetails.getDrugName());
		drug.setGeneticName(drugDetails.getGeneticName());
		drug.setType(drugDetails.getType());
		drug.setAvailability(drugDetails.isAvailability());
		String email=principal.getName();
		if (drugDetails.isAvailability()==true){
			drugServices.sendEmailNotification(email, drugDetails);
		}
		Drug updatedDrug = drugRepository.save(drug);
		return ResponseEntity.ok(updatedDrug);
	}

	// delete drug rest api
	@DeleteMapping("/drugs/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteDrug(@PathVariable Long id){
		Drug drug = drugRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Drug not exist with id :" + id));

		drugRepository.delete(drug);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	 @GetMapping("/search/")
	    public List<Object []> viewSimilarProducts(Model model, @Param("keyword") String keyword) {
	        List<Object []> listDrugs = drugServices.searchDrug(keyword);
	         
	        return listDrugs;
	    }

	
	
	@GetMapping("/similar-products")		
	public ResponseEntity<Drug> getSimilarProduts(@PathVariable Long id) {						
	return null;
}

	
	

}

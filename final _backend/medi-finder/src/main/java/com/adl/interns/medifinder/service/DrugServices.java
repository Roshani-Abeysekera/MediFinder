package com.adl.interns.medifinder.service;

import com.adl.interns.medifinder.entity.Drug;
import com.adl.interns.medifinder.entity.RequestProduct;
import com.adl.interns.medifinder.exception.ResourceNotFoundException;
import com.adl.interns.medifinder.repository.DrugRepository;
import com.adl.interns.medifinder.repository.RequestProductRepository;
import com.adl.interns.medifinder.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DrugServices {
	private Logger log=LoggerFactory.getLogger(DrugServices.class);

    private DrugRepository drugRepository;

	private RequestProductRepository requestProductRepository;

	private UserRepository userRepository;

	private SearchService searchService;

	private EmailVerificationService emailVerificationService;

	@Autowired
	public DrugServices(DrugRepository drugRepository
			, RequestProductRepository requestProductRepository
			, UserRepository userRepository, SearchService searchService
			, EmailVerificationService emailVerificationService) {
		this.drugRepository = drugRepository;
		this.requestProductRepository = requestProductRepository;
		this.userRepository = userRepository;
		this.searchService = searchService;
		this.emailVerificationService=emailVerificationService;
	}


	// get all drugs
	public List<Drug> getAllDrugs(){
		log.info("view all drug");
		log.info(drugRepository.findAll().toString());
		return drugRepository.findAll();
	}		

	public Drug createDrug(String email,Drug drug){
		sendEmailNotification(email, drug);
		return drugRepository.save(drug);
	}

	public void sendEmailNotification(String email, Drug drug) {
		List<RequestProduct> requestProducts=requestProductRepository.
				findAllByDrugNameAndNotificationIsSent(drug.getDrugName(),false);
		double latitude=userRepository.findByEmail(email).getLatitude();
		double longitude=userRepository.findByEmail(email).getLongitude();
		if(requestProducts!=null){
			for (RequestProduct result: requestProducts) {
				if(searchService.distance(latitude, longitude, result.getLatitude(),result.getLongitude())<10){
					log.info("send email notification");
					SimpleMailMessage mailMessage = new SimpleMailMessage();
					mailMessage.setTo(result.getEmail());
					mailMessage.setSubject("Drug availability notification");
					mailMessage.setFrom("medifinder2021@gmail.com");
					mailMessage.setText("The drug "+result.getDrugName()+" now available");
					emailVerificationService.sendEmail(mailMessage);
					result.setNotificationIsSent(true);
				}
			}
		}
	}

	// get drug by id rest api
	public ResponseEntity<Drug> getDrugById(Long id) {
		Drug drug = drugRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Drug not exist with id :" + id));
		return ResponseEntity.ok(drug);
	}

	// update drug rest api
	public ResponseEntity<Drug> updateDrug(Long id,Drug drugDetails){
		Drug drug = drugRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Drug not exist with id :" + id));

		drug.setDrugName(drugDetails.getDrugName());
		drug.setGeneticName(drugDetails.getGeneticName());
		drug.setType(drugDetails.getType());
		drug.setAvailability(drugDetails.isAvailability());

		Drug updatedDrug = drugRepository.save(drug);
		return ResponseEntity.ok(updatedDrug);
	}

	// delete drug rest api
	public ResponseEntity<Map<String, Boolean>> deleteDrug( Long id){
		Drug drug = drugRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Drug not exist with id :" + id));

		drugRepository.delete(drug);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	
	 public List<Object []> searchDrug(String keyword) {
	      
         return drugRepository.search(keyword);
     
  //   return drugRepository.findAll();
     
}



}

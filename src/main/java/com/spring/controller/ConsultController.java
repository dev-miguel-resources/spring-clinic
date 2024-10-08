package com.spring.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
//import java.time.LocalDate;
import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

//import com.cloudinary.Cloudinary;
import com.spring.dto.ConsultDTO;
import com.spring.dto.ConsultListExamDTO;
import com.spring.dto.ConsultProcDTO;
import com.spring.dto.FilterConsultDTO;
import com.spring.dto.IConsultProcDTO;
//import com.spring.dto.ConsultRecord;
import com.spring.model.Consult;
import com.spring.model.Exam;
import com.spring.model.MediaFile;
import com.spring.service.IConsultService;
import com.spring.service.IMediaFileService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/consults")
@RequiredArgsConstructor
public class ConsultController {

    private final IConsultService service;

    @Qualifier("consultMapper")
    private final ModelMapper mapper;
    // private final Cloudinary cloudinary;
    private final IMediaFileService mfService;

    private ConsultDTO convertToDto(Consult obj) {
        return mapper.map(obj, ConsultDTO.class);
    }

    private Consult convertToEntity(ConsultDTO dto) {
        return mapper.map(dto, Consult.class);
    }

    // debo traer todos los servicios implementados para crear mis apis rest
    @GetMapping
    // public ResponseEntity<List<ConsultRecord>> findAll() {
    public ResponseEntity<List<ConsultDTO>> findAll() throws Exception {
        // forma 1 genérica
        /*
         * List<ConsultDTO> listExample = service.findAll().stream().map(e ->
         * convertToDto(e)).toList();
         * return new ResponseEntity<>(listExample, HttpStatus.OK);
         */
        // forma 2 moderna
        List<ConsultDTO> list = service.findAll().stream().map(this::convertToDto).toList();
        return new ResponseEntity<>(list, HttpStatus.OK);
        // forma 3
        /*
         * List<ConsultRecord> list = service.findAll().stream().map(e ->
         * new ConsultRecord(e.getIdConsult(), e.getFirstName(), e.getLastName(),
         * e.getDni(), e.getAddress(), e.getPhone(), e.getEmail())
         * ).toList();
         * return new ResponseEntity<>(list, HttpStatus.OK);
         */
    }

    @PostMapping() // nivel de madurez 3
    public ResponseEntity<ConsultDTO> save(@Valid @RequestBody ConsultListExamDTO dto) {
        Consult cons = this.convertToEntity(dto.getConsult());

        // alternativa 1
        // List<Exam> exams = dto.getLstExam().stream().map(e -> mapper.map(e,
        // Exam.class)).toList();

        // alternativa 2
        List<Exam> exams = mapper.map(dto.getLstExam(), new TypeToken<List<Exam>>() {
        }.getType());

        Consult obj = service.saveTransactional(cons, exams);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getIdConsult())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultDTO> findById(@PathVariable("id") Integer id) throws Exception {
        Consult obj = service.findById(id);
        return new ResponseEntity<>(convertToDto(obj), HttpStatus.OK);
    }

    // forma 2 con hateoas
    @GetMapping("/hateoas/{id}")
    public EntityModel<ConsultDTO> findByHateoas(@PathVariable("id") Integer id) throws Exception {
        EntityModel<ConsultDTO> resource = EntityModel.of(convertToDto(service.findById(id))); // la salida

        WebMvcLinkBuilder link1 = linkTo(methodOn(this.getClass()).findById(id));

        resource.add(link1.withRel("consult-info1"));
        resource.add(link1.withRel("consult-info2"));

        return resource;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consult> update(@PathVariable("id") Integer id, @RequestBody ConsultDTO dto)
            throws Exception {
        Consult obj = service.update(convertToEntity(dto), id);
        return new ResponseEntity<>(obj, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Consult> delete(@PathVariable("id") Integer id) throws Exception {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/search/others")
    public ResponseEntity<List<ConsultDTO>> searchByOthers(@RequestBody FilterConsultDTO filterDTO) {
        List<Consult> consults = service.search(filterDTO.getDni(), filterDTO.getFullname());

        // forma 1
        List<ConsultDTO> consultDTOs = consults.stream().map(e -> mapper.map(e, ConsultDTO.class)).toList();
        // forma 2
        // List<ConsultDTO> consultDTOs = mapper.map(consults, new
        // TypeToken<List<ConsultDTO>>(){}.getType());

        return new ResponseEntity<>(consultDTOs, HttpStatus.OK);
    }

    @GetMapping("/search/dates")
    public ResponseEntity<List<ConsultDTO>> searchByDates(
            @RequestParam(value = "date1", required = true) String date1,
            @RequestParam(value = "date2", required = true) String date2) {
        /*
         * if (date1 == null || date1.isEmpty()) {
         * date1 = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
         * }
         */
        List<Consult> consults = service.searchByDates(LocalDateTime.parse(date1), LocalDateTime.parse(date2));
        List<ConsultDTO> consultDTOs = mapper.map(consults, new TypeToken<List<ConsultDTO>>() {
        }.getType());

        return new ResponseEntity<>(consultDTOs, HttpStatus.OK);
    }

    @GetMapping("/callProcedureNative")
    public ResponseEntity<List<ConsultProcDTO>> callProcedureOrFunctionNative() {
        List<ConsultProcDTO> consults = service.callProcedureOrFunctionNative();
        return new ResponseEntity<>(consults, HttpStatus.OK);
    }

    @GetMapping("/callProcedureProjection")
    public ResponseEntity<List<IConsultProcDTO>> callProcedureOrFunctionProjection() {
        List<IConsultProcDTO> consults = service.callProcedureOrFunctionProjection();
        return new ResponseEntity<>(consults, HttpStatus.OK);
    }

    // forma 1: el dev frontend luego tiene que hacer la transformación a un formato
    // x
    // @GetMapping(value = "/generateReport", produces =
    // MediaType.APPLICATION_OCTET_STREAM_VALUE)
    // forma 2: devolverlo procesado desde el backend
    /*
     * @GetMapping(value = "/generateReport", produces =
     * MediaType.APPLICATION_PDF_VALUE)
     * public ResponseEntity<byte[]> generateReport() throws Exception {
     * byte[] data = service.generateReport();
     * return new ResponseEntity<>(data, HttpStatus.OK);
     * }
     */

    @GetMapping(value = "/generateReport", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> generateReport() throws Exception {
        byte[] data = service.generateReport();

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/saveFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> saveFile(@RequestParam("file") MultipartFile file) throws Exception {
        // DB
        MediaFile mf = new MediaFile();
        mf.setFileType(file.getContentType());
        mf.setFilename(file.getOriginalFilename());
        mf.setValue(file.getBytes());

        mfService.save(mf);

        // Repo Externo
        /*
         * File f = this.convertToFile(file); // convertir un multipart file a file
         * Map response = cloudinary.uploader().upload(f,
         * ObjectUtils.asMap("resource_type", "auto")); // automático puede
         * cualquier archivo
         * JSONObject json = new JSONObject(response); // recuperar el bloque de
         * response
         * String url = json.getString("url"); // la url del recurso
         * 
         * System.out.println(url);
         * //mfService.update(url);
         */

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/readFile/{idFile}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> readFile(@PathVariable("idFile") Integer idFile) throws Exception {

        byte[] arr = mfService.findById(idFile).getValue();

        return new ResponseEntity<>(arr, HttpStatus.OK);
    }

    // esto es para recuperarlo correctamente desde el cliente, ya que lo se ocupa
    // el formato es File
    public File convertToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream outputStream = new FileOutputStream(file);
        outputStream.write(multipartFile.getBytes());
        outputStream.close();
        return file;
    }
}

package softuni.exam.service;

import softuni.exam.models.entity.Part;

import java.io.IOException;
import java.util.List;

// TODO: Implement all methods
public interface PartService {

    boolean areImported();

    String readPartsFileContent() throws IOException;

    String importParts() throws IOException;
    List<Part> findAll();
    Long findOne(Long id);
}

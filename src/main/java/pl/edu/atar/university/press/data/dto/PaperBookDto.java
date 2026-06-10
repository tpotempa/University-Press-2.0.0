package pl.edu.atar.university.press.data.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class PaperBookDto {

    private String isbn;
    private String title;
    private String description;
    private LocalDate publishingDate;
    private String binding;
    private List<AuthorDto> authors = new ArrayList<>();
    private List<CategoryDto> categories = new ArrayList<>();
}
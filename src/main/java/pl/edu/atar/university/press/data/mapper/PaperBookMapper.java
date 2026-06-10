package pl.edu.atar.university.press.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.edu.atar.university.press.data.dto.CategoryDto;
import pl.edu.atar.university.press.data.dto.PaperBookDto;
import pl.edu.atar.university.press.data.model.Category;
import pl.edu.atar.university.press.data.model.PaperBook;


@Mapper(componentModel = "spring")
public interface PaperBookMapper {

    PaperBookDto toBookDto(PaperBook paperBook);

    @Mapping(source = "name", target = "genre")
    CategoryDto toCategoryDto(Category category);
}
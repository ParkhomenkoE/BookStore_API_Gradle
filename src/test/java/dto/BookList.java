package dto;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class BookList {
    private String userId;

    private ArrayList<Object> collectionOfIsbns;
}

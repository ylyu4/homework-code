package tw.training.homework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ErrorBody {

    private Integer status;

    private String message;

}

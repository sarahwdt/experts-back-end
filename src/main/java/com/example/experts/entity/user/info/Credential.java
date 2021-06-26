package com.example.experts.entity.user.info;

import com.example.experts.entity.abstraction.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

/**
 * Абстрактный класс данных пользователя
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Credential extends BaseEntity {
    @Size(min = 2, message = "Имя не может быть короче двух символов")
    private String firstName;
    @Size(min = 2, message = "Фамилия не может быть короче двух символов")
    private String secondName;

    private String middleName;
    @Size(min = 12, max = 12, message = "Формат номера телефона +7XXXXXXXXXX")
    private String phone;
    @Email(regexp = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$",
            message = "Невалидный e-mail")
    private String email;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Degree degree;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Rank rank;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Position position;

    @ManyToOne(cascade = CascadeType.DETACH)
    private ScientificDirection direction;

    @Override
    public void setId(Long id) {
        super.setId(id);
    }
}

package me.rate.rateme.data.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyModel {

  private String id;
  private String name;
  private String email;
  private List<UserModel> employees;
  private List<ContestModel> contests;
}

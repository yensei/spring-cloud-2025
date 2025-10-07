package py.com.yensei.mcs.customers.models;

import java.time.LocalDate;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class CustomerModel  {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private LocalDate birthdate;
    
}

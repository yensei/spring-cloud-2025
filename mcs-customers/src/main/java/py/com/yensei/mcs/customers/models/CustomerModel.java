package py.com.yensei.mcs.customers.models;

import java.io.Serializable;
import java.sql.Date;

@lombok.Data
@lombok.AllArgsConstructor
@lombok.NoArgsConstructor
public class CustomerModel implements Serializable {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String address;
    private Date birthdate;
    
}

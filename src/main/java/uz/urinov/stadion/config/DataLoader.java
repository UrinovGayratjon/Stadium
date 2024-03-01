package uz.urinov.stadion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.urinov.stadion.entity.StadiumEntity;
import uz.urinov.stadion.entity.UserEntity;
import uz.urinov.stadion.entity.enums.RoleEnum;
import uz.urinov.stadion.repository.StadiumRepository;
import uz.urinov.stadion.repository.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StadiumRepository stadiumRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {

            userRepository.save(
                    UserEntity.builder()
                            .fullName("Admin")
                            .role(RoleEnum.ADMIN)
                            .username("admin")
                            .password(passwordEncoder.encode("admin"))
                            .build()
            );
            userRepository.save(
                    UserEntity.builder()
                            .fullName("User")
                            .role(RoleEnum.USER)
                            .username("user")
                            .password(passwordEncoder.encode("user"))
                            .build()
            );
            UserEntity save = userRepository.save(
                    UserEntity.builder()
                            .fullName("User")
                            .role(RoleEnum.STADIUM_OWNER)
                            .username("owner")
                            .password(passwordEncoder.encode("owner"))
                            .build()
            );

            StadiumEntity stadiumEntity = new StadiumEntity();
            stadiumEntity.setName("Telavishka");
            stadiumEntity.setLat(32.33);
            stadiumEntity.setLon(34.4);
            stadiumEntity.setAddress("Yunisobod");
            stadiumEntity.setOwner(save);
            stadiumEntity.setContact("+998912345678");
            stadiumEntity.setHourlyPrice(120_000L);

            StadiumEntity stadiumEntity2 = new StadiumEntity();
            stadiumEntity2.setName("Bodomzor");
            stadiumEntity2.setLat(23.21);
            stadiumEntity2.setLon(20.4);
            stadiumEntity2.setAddress("Yunisobod");
            stadiumEntity2.setOwner(save);
            stadiumEntity2.setContact("+998912345678");
            stadiumEntity2.setHourlyPrice(120_000L);

            StadiumEntity stadiumEntity3 = new StadiumEntity();
            stadiumEntity3.setName("Chilonzor");
            stadiumEntity3.setLat(44.33);
            stadiumEntity3.setLon(39.4);
            stadiumEntity3.setAddress("Yunisobod");
            stadiumEntity3.setOwner(save);
            stadiumEntity3.setContact("+998912345678");
            stadiumEntity3.setHourlyPrice(120_000L);


            stadiumRepository.save(stadiumEntity);
            stadiumRepository.save(stadiumEntity2);
            stadiumRepository.save(stadiumEntity3);

        }
    }
}

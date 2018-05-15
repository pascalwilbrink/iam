package nl.wilbrink.config;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES;
import static com.fasterxml.jackson.databind.MapperFeature.DEFAULT_VIEW_INCLUSION;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static java.lang.String.format;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getGlobal;
import static org.springframework.orm.jpa.vendor.Database.H2;
import static org.springframework.orm.jpa.vendor.Database.POSTGRESQL;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.FlywayException;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.PostgreSQL9Dialect;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
@ComponentScan(
    basePackages = "nl.wilbrink"
)
@EnableJpaRepositories("nl.wilbrink")
@EnableJpaAuditing
public class ApplicationConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    private Logger logger = getGlobal();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Primary
    ObjectMapper objectMapper() {
        final ObjectMapper mapper = new ObjectMapper();

        mapper.configure(ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DEFAULT_VIEW_INCLUSION, false);
        mapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationInclusion(NON_NULL);

        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

        entityManager.setDataSource(dataSource);
        entityManager.setPackagesToScan("nl.wilbrink");
        entityManager.setJpaVendorAdapter(jpaVendorAdapter());
        entityManager.setPersistenceProviderClass(HibernatePersistenceProvider.class);

        return entityManager;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setShowSql(true);

        if (determineDatabase().equals(H2)) {
            jpaVendorAdapter.setDatabase(H2);
            jpaVendorAdapter.setDatabasePlatform(H2Dialect.class.getName());
        } else if (determineDatabase().equals(POSTGRESQL)) {
            jpaVendorAdapter.setDatabase(POSTGRESQL);
            jpaVendorAdapter.setDatabasePlatform(PostgreSQL9Dialect.class.getName());
        }

        jpaVendorAdapter.setGenerateDdl(true);

        return jpaVendorAdapter;
    }

    @Bean
    Flyway flyway() {
        Flyway flyway = new Flyway();

        flyway.setDataSource(dataSource);

        final String[] locations = this.getFlywayLocations();

        if (locations.length > 0) {
            flyway.setLocations(locations);

            try {
                flyway.migrate();
            } catch(FlywayException e) {
                logger.log(SEVERE, format("Flyway error: %s", e.getMessage()));
            }
        }

        return flyway;
    }

    private Database determineDatabase() {
        Database database;

        String db = env.getProperty("database", "H2").toUpperCase();

        if (db.equals("H2")) {
            database = H2;
        } else if (db.equals("POSTGRESQL")) {
            database = POSTGRESQL;
        } else {
            database = H2;
        }

        return database;
    }

    private String[] getFlywayLocations() {
        switch (determineDatabase()) {
            case H2:
                return new String[]{"migrations/h2"};
            case POSTGRESQL:
                return new String[]{"migrations/postgres"};
            default:
                return new String[]{};
        }
    }

}

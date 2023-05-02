package hello.datasource;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.hibernate.validator.constraints.time.DurationMax;
import org.hibernate.validator.constraints.time.DurationMin;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

//ConfigurationProperties 장점
//외부 설정을 객체로 편리하게 변환해서 사용할 수 있다.
//외부 설정의 계층을 객체로 편리하게 표현할 수 있다.
//외부 설정을 타입 안전하게 사용할 수 있다.
//검증기를 적용할 수 있다.
@Getter
@ConfigurationProperties("my.datasource")  //@ConfigurationProperties 이 있으면 외부 설정을 주입 받는 객체라는 뜻이다
@Validated
public class MyDataSourcePropertiesV3 {

    @NotEmpty
    private String url;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    private Etc etc;

    @Getter
    public static class Etc {

        @Min(1) @Max(999)
        private int maxConnection;

        @DurationMin(seconds = 1)
        @DurationMax(seconds = 60)
        private Duration timeout;
        private List<String> options = new ArrayList<>();

        public Etc(int maxConnection, Duration timeout, List<String> options) {
            this.maxConnection = maxConnection;
            this.timeout = timeout;
            this.options = options;
        }
    }


    //참고 @ConstructorBinding
    // 스프링 부트 3.0 이전에는 생성자 바인딩 시에 @ConstructorBinding 애노테이션을 필수로 사용해야 했다.
    // 스프링 부트 3.0 부터는 생성자가 하나일 때는 생략할 수 있다. 생성자가 둘 이상인 경우에는 사용할 생성자에 @ConstructorBinding 애노테이션을 적용하면 된다.
    //@ConstructorBinding
    public MyDataSourcePropertiesV3(String url, String username, String password, Etc etc) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.etc = etc;
    }
}

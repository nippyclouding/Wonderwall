package Project.Ex.sign.sign_domain.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor((new SignInCheckInterceptor()))
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/signIn", "/signUp", "/signUpSuccess", "/css/**", "/error");
                //html exlude
                //"/error" 파일은 스프링이 자동 생성하는 표준 파일이다.
    }
}

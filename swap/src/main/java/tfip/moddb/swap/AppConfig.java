package tfip.moddb.swap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tfip.moddb.swap.service.JwtFilter;


@Configuration
public class AppConfig {
    @Autowired
    private JwtFilter myFilter;

    @Bean
    public FilterRegistrationBean<JwtFilter> registerJwtFilter(JwtFilter filter) {
        FilterRegistrationBean<JwtFilter> regFilterBean = new FilterRegistrationBean<>();
        regFilterBean.setFilter(filter);
        regFilterBean.addUrlPatterns("/api/secure/*");
        regFilterBean.addUrlPatterns("/api/delete");
        regFilterBean.setEnabled(true);
        return regFilterBean;
    }
}

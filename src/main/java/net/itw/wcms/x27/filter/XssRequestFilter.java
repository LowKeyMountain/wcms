package net.itw.wcms.x27.filter;
 
import org.springframework.web.filter.OncePerRequestFilter;
 
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.itw.wcms.x27.utils.XssRequestWrapper;
import java.io.IOException;
 
/**
 * @Auther: sparking
 * @Date: 2022/2/22 09:33
 * @Description: 处理xss攻击的过滤器
 */
public class XssRequestFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 将request通过自定义的装饰类进行装饰
        XssRequestWrapper xssRequest = new XssRequestWrapper((HttpServletRequest) request);
        filterChain.doFilter(xssRequest, response);
    }
}
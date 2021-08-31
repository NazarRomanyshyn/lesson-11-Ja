package ua.lviv.lgs.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import ua.lviv.lgs.domain.AccessLevel;

@WebFilter("/cabinet.jsp")
public class CabinetFilter implements Filter {
	private FilterService filterService = FilterService.getFilterService();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		filterService.doFilterValidation(request, response, chain, Arrays.asList(AccessLevel.USER, AccessLevel.ADMIN));
	}
}

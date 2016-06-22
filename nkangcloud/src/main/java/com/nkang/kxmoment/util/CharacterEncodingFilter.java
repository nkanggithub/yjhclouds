package com.nkang.kxmoment.util;

import javax.servlet.*;
import java.io.IOException;
 
 public class CharacterEncodingFilter implements Filter {
 
    private String encoding = "UTF-8";
    private boolean forceEncoding = true;
 
    /**
     * Set the encoding to use for requests. This encoding will be
     * passed into a ServletRequest.setCharacterEncoding call.
     * <p>Whether this encoding will override existing request
     * encodings depends on the "forceEncoding" flag.
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
 
    /**
     * Set whether the encoding of this filter should override existing
     * request encodings. Default is "false", i.e. do not modify encoding
     * if ServletRequest.getCharacterEncoding returns a non-null value.
     */
    public void setForceEncoding(boolean forceEncoding) {
        this.forceEncoding = forceEncoding;
    }
 
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
        String force = filterConfig.getInitParameter("forceEncoding");
        this.forceEncoding = (force == null) || Boolean.valueOf(force).booleanValue() ;
    }
 
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        if (this.forceEncoding || servletRequest.getCharacterEncoding() == null) {
            servletRequest.setCharacterEncoding(this.encoding);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
 
    public void destroy() {
 
    }
 
}
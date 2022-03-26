
## 代码
one DelegatingFilterProxy----> one DelegatingFilterProxy(name=springSecurityFilterChain)
---->  n SecurityFilterChain (HttpSecurity,ignoring url)

one SecurityFilterChain ----> n filters(login,logout)

DelegatingFilterProxy:
class: SecurityFilterAutoConfiguration

DelegatingFilterProxy:
class:WebSecurityConfiguration.springSecurityFilterChain
class:WebSecurity.performBuild
```java
for (RequestMatcher ignoredRequest : ignoredRequests) {
    securityFilterChains.add(new DefaultSecurityFilterChain(ignoredRequest));
}
for (SecurityBuilder<? extends SecurityFilterChain> securityFilterChainBuilder : securityFilterChainBuilders) {
    securityFilterChains.add(securityFilterChainBuilder.build());
}
FilterChainProxy filterChainProxy = new FilterChainProxy(securityFilterChains);
if (httpFirewall != null) {
    filterChainProxy.setFirewall(httpFirewall);
}
```
class:HttpSecurity.performBuild(DefaultSecurityFilterChain)

1. /login,GET
2. /login,POST
3. /login?error
4. /login?logout

```java
/*
	 * <ul>
	 * <li>/login GET - the login form</li>
	 * <li>/login POST - process the credentials and if valid authenticate the user</li>
	 * <li>/login?error GET - redirect here for failed authentication attempts</li>
	 * <li>/login?logout GET - redirect here after successfully logging out</li>
	 * </ul>
	 *
	 * If "/authenticate" was passed to this method it update the defaults as shown below:
	 *
	 * <ul>
	 * <li>/authenticate GET - the login form</li>
	 * <li>/authenticate POST - process the credentials and if valid authenticate the user
	 * </li>
	 * <li>/authenticate?error GET - redirect here for failed authentication attempts</li>
	 * <li>/authenticate?logout GET - redirect here after successfully logging out</li>
	 * </ul>
	 */

```


1. @EnableWebSecurity
2. WebSecurityConfiguration.springSecurityFilterChain,build WebSecurity,springSecurityFilterChain
3. WebSecurityConfiguration.setFilterChainProxySecurityConfigurer, pass HttpSecurity
4. WebSecurityConfigurerAdapter,build HttpSecurity

one WebSecurity --> n HttpSecurity

An authentication success strategy which can make use of the {@link org.springframework.security.web.savedrequest.DefaultSavedRequest} which may have been stored in the session by the {@link ExceptionTranslationFilter}.
SavedRequestAwareAuthenticationSuccessHandler
重定向到登陆前的url

ExceptionTranslationFilter
捕获异常，保存http,重定向到 /login

FormLoginConfigurer,LoginUrlAuthenticationEntryPoint
private void setLoginPage(String loginPage) {
	this.loginPage = loginPage;
	this.authenticationEntryPoint = new LoginUrlAuthenticationEntryPoint(loginPage);
}

HttpSessionRequestCache
重定向真正的url

WebAsyncManagerIntegrationFilter:
将 SecurityContext 与 WebAsyncManager 整合起来，使得在 Callable 中可以使用 SecurityContext

SecurityContextPersistenceFilter:
有两个功能：1）在每次请求前，向 SecurityContextHolder 中添加 SecurityContext 对象，在请求结束后再清空该对象；2）请求结束之后，向 Session 中添加 SecurityContext 对象，以便下次请求使用。

HeaderWriterFilter:
处于安全的目的，向 http 响应添加一些 Header, 比如 X-Frame-Options, X-XSS-Protection*，X-Content-Type-Options.

LogoutFilter:
拦截退出请求，注销用户的认证及会议信息，最后跳转到指定页面。

UsernamePasswordAuthenticationFilter：
拦截登录请求，获取用户表单信息，生成 UsernamePasswordAuthenticationToken 对象，调用 AuthenticationManager 对象的 authenticate 认证方法，完成用户的认证流程，
最终生成认证结果对象 Authentication，并将该对象添加到 SecurityContextHolder 中。

RequestCacheAwareFilter：
未认证的请求认证成功之后，需要恢复 HttpRequest 的状态，如 Method,Locales,Parameters 等等，RequestCacheAwareFilter 就是用来恢复请求的状态。 

SecurityContextHolderAwareRequestFilter：
将 HttpServletRequest 对象扩展为 SecurityContextHolderAwareRequestWrapper 对象，它向 HttpServletRequest 中添加了额外的方法：
1）authenticate,允许用户决他们是否已经认证成功或未认证跳转到登录页面；
2）login，允许用户进行认证操作；
3）logout，允许用户进行登出操作；

AnonymousAuthenticationFilter：
判断 SecurityContextHolder 是否有 Authentication 对象，没有的话则添加一个用户名为 anonymousUser、角色为 ROLE_ANONYMOUS 的 Authentication 对象。

SessionManagementFilter：
加入一些与 Session 相关的操作，如激活 session-fixation 保护机制或检测一个用户多次登陆的问题。

ExceptionTranslationFilter：
处理转译 AccessDeniedException 和 AuthenticationException 异常。检测到 AuthenticationException 异常，重定向到登陆页面，并缓存 RequestCache 对象，便于认证成功之后恢复请求。
检测到 AccessDeniedException，如果是匿名用户，则重定向到登陆页面，否则由 AccessDeniedHandlerImpl 对象处理。

FilterSecurityInterceptor：
这个过滤器判断认证用户是否具备访问资源（url endpoint）的权限。

SpringSecurity:
WebAsyncManagerIntegrationFilter
SecurityContextPersistenceFilter
HeaderWriterFilter
LogoutFilter
UsernamePasswordAuthenticationFilter
RequestCacheAwareFilter
SecurityContextHolderAwareRequestFilter
AnonymousAuthenticationFilter
SessionManagementFilter
ExceptionTranslationFilter
FilterSecurityInterceptor

Authorization-Server:
WebAsyncManagerIntegrationFilter
SecurityContextPersistenceFilter
HeaderWriterFilter
LogoutFilter
ClientCredentialsTokenEndpointFilter
BasicAuthenticationFilter
RequestCacheAwareFilter
SecurityContextHolderAwareRequestFilter
AnonymousAuthenticationFilter
SessionManagementFilter
ExceptionTranslationFilter
FilterSecurityInterceptor

## Roadmap
1. SpringSecurity 整体流程
2. SpringSecurity 认证
3. SpringSecurity 授权
4. SpringSecurity 自定义认证
5. Oauth2 自定义授权
6. Springcloud RPC
7. Springcloud sentinel
8. Oauth2 SSO
9. Springboot Cache

ProviderManager
DaoAuthenticationProvider

```java
AbstractUserDetailsAuthenticationProvider

protected Authentication createSuccessAuthentication(Object principal,
			Authentication authentication, UserDetails user) {
		// Ensure we return the original credentials the user supplied,
		// so subsequent attempts are successful even with encoded passwords.
		// Also ensure we return the original getDetails(), so that future
		// authentication events after cache expiry contain the details
		UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				principal, authentication.getCredentials(),
				authoritiesMapper.mapAuthorities(user.getAuthorities()));
		result.setDetails(authentication.getDetails());

		return result;
	}

```





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




1. @EnableWebSecurity
2. WebSecurityConfiguration.springSecurityFilterChain,build WebSecurity,springSecurityFilterChain
3. WebSecurityConfiguration.setFilterChainProxySecurityConfigurer, pass HttpSecurity
4. WebSecurityConfigurerAdapter,build HttpSecurity

one WebSecurity --> n HttpSecurity



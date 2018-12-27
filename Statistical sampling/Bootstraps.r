#### Example 1: Toy example for bootstrap

X = c(4,3,5,2,6)
Xbar = mean(X)

set.seed(4231)
N = 1000
xbar.boot = numeric(N)
for (i in 1:N) {
	x.boot = sample(X, replace=T)
	xbar.boot[i] = mean(x.boot)
}


x.boot.all = expand.grid(rep(list(X), 5))		# obtain all 3125 possible bootstrap samples
xbar.exact = rowMeans(x.boot.all)

### plot bootstrap ecdf of sample mean with its "truth"
plot(ecdf(xbar.exact), verticals=TRUE, do.points=FALSE, lwd = 2, col='red', main="Bootstrap ecdf", cex.main=1.5)
plot(ecdf(xbar.boot), add=TRUE, verticals=TRUE, do.points=FALSE, col='blue')
legend(4.3,0.34, cex=1.3,
	 c(expression(P(bar(X)^"*"<=t*"|"*X)),"Bootstrap ecdf"),
       lwd=rep(2,2),
	 col=c("red","blue"))

### bootstrap estimate for P(\bar X^* - \bar X > 2)
mean(xbar.boot - Xbar > 2)
	
#"Exact P(Xbar^star <=t)", 
	
#### Example 2: Bootstrap for linear regression model	
### We use the mtcars data in R as an example

N = 1000	# number of bootstrap samples

n = nrow(mtcars)	# sample size
y = mtcars$mpg		# response variable
wt = mtcars$wt		# covariate 1
disp = mtcars$disp	# covariate 2
X = cbind (1, wt, disp)		# weighting matrix
X.invcross = solve(crossprod(X))		# save this matrix for future use
beta.hat = X.invcross %*% crossprod(X, y)	# OLS estimator
y.hat = X %*% beta.hat		# fitted values
res.hat = y - y.hat			# residuals
sigma2.hat = sum(res.hat^2)/(n-3)	# estimated error variance
se.hat = sqrt(diag(sigma2.hat * X.invcross))

set.seed(4231)
### residual bootstrap
beta.res.boot = matrix(0, nrow = N, ncol = 3)
se.res.boot = matrix(0, nrow = N, ncol = 3)		# bootstrap s.e.
for (b in 1:N) {
	res.boot = sample(res.hat, replace=TRUE)
	y.boot = y.hat + res.boot
	beta.est = X.invcross %*% crossprod(X, y.boot)
	res.boot = y.boot - X %*% beta.est
	sigma2.boot = sum(res.boot^2)/(n-3)
	var.beta.boot = sigma2.boot * X.invcross
	beta.res.boot[b,] = beta.est
	se.res.boot[b,] = sqrt(diag(var.beta.boot))
}
colMeans(beta.res.boot)		# check the column means and see if they match with the OLS estimator

### parametric bootstrap
beta.para.boot = matrix(0, nrow = N, ncol = 3)
se.para.boot = matrix(0, nrow = N, ncol = 3)	# bootstrap s.e.
for (b in 1:N) {
	res.boot = rnorm(n, sd=sqrt(sigma2.hat))
	y.boot = y.hat + res.boot
	beta.est = X.invcross %*% crossprod(X, y.boot)
	res.boot = y.boot - X %*% beta.est
	sigma2.boot = sum(res.boot^2)/(n-3)
	var.beta.boot = sigma2.boot * X.invcross
	beta.para.boot[b,] = beta.est
	se.para.boot[b,] = sqrt(diag(var.beta.boot))
}
colMeans(beta.para.boot)		# check the column means and see if they match with the OLS estimator

### wild bootstrap
beta.wild.boot = matrix(0, nrow = N, ncol = 3)
se.wild.boot = matrix(0, nrow = N, ncol = 3)	# bootstrap s.e.
for (b in 1:N) {
	v.boot = 2*rbinom(n,1,0.5) - 1	# transform from Bernoulli random variates
	y.boot = y.hat + v.boot * res.hat
	beta.est = X.invcross %*% crossprod(X, y.boot)
	res.boot = y.boot - X %*% beta.est
	sigma2.boot = sum(res.boot^2)/(n-3)
	var.beta.boot = sigma2.boot * X.invcross
	beta.wild.boot[b,] = beta.est
	se.wild.boot[b,] = sqrt(diag(var.beta.boot))
}
colMeans(beta.wild.boot)		# check the column means and see if they match with the OLS estimator

### plot histograms for the bootstrap estimates
brk1 = seq(-7,1,by=0.5)
brk2 = seq(-0.05,0.015,by=0.005)
par(mfrow=c(2,3))
hist(beta.res.boot[,2], freq=F, col="cyan", breaks=brk1, main="Residual bootstrap for beta1", xlab="beta1")
hist(beta.para.boot[,2], freq=F, col="cyan", breaks=brk1, main="Parametric bootstrap for beta1", xlab="beta1")
hist(beta.wild.boot[,2], freq=F, col="cyan", breaks=brk1, main="Wild bootstrap for beta1", xlab="beta1")
hist(beta.res.boot[,3], freq=F, col="cyan", breaks=brk2, main="Residual bootstrap for beta2", xlab="beta2")
hist(beta.para.boot[,3], freq=F, col="cyan", breaks=brk2, main="Parametric bootstrap for beta2", xlab="beta2")
hist(beta.wild.boot[,3], freq=F, col="cyan", breaks=brk2, main="Wild bootstrap for beta2", xlab="beta2")

### 95% two-sided pivotal C.I. for 3 types of bootstraps, for beta1 and beta2, respectively

2*beta.hat[2] - quantile(beta.res.boot[,2], c(0.95,0.05))
2*beta.hat[2] - quantile(beta.para.boot[,2], c(0.95,0.05))
2*beta.hat[2] - quantile(beta.wild.boot[,2], c(0.95,0.05))

2*beta.hat[3] - quantile(beta.res.boot[,3], c(0.95,0.05))
2*beta.hat[3] - quantile(beta.para.boot[,3], c(0.95,0.05))
2*beta.hat[3] - quantile(beta.wild.boot[,3], c(0.95,0.05))

### 95% two-sided studentized C.I. for 3 types of bootstraps, for beta1 and beta2, respectively

beta.hat[2] - se.hat[2] * quantile((beta.res.boot[,2]-beta.hat[2])/se.res.boot[,2], c(0.95,0.05))
beta.hat[2] - se.hat[2] * quantile((beta.para.boot[,2]-beta.hat[2])/se.para.boot[,2], c(0.95,0.05))
beta.hat[2] - se.hat[2] * quantile((beta.wild.boot[,2]-beta.hat[2])/se.wild.boot[,2], c(0.95,0.05))	

beta.hat[3] - se.hat[3] * quantile((beta.res.boot[,3]-beta.hat[3])/se.res.boot[,3], c(0.95,0.05))
beta.hat[3] - se.hat[3] * quantile((beta.para.boot[,3]-beta.hat[3])/se.para.boot[,3], c(0.95,0.05))
beta.hat[3] - se.hat[3] * quantile((beta.wild.boot[,3]-beta.hat[3])/se.wild.boot[,3], c(0.95,0.05))

### in comparison to the asymptotic normal C.I.
beta.hat[2] + se.hat[2] * qnorm(c(0.05,0.95))
beta.hat[3] + se.hat[3] * qnorm(c(0.05,0.95))


library(coda)

### Example 1: Random walk Metropolis algorithm 
### for normal data with a Cauchy prior

set.seed(4231)
n = 1000
T = 10^4
delta2 = 5.76/n
theta_true = 0

# simulate the data
y = rnorm(n, theta_true, 1)

# random walk Metropolis algorithm
theta.trace = numeric(T)
accept = numeric(T)		# record acceptances and rejections
theta.init = 1		# initial value
theta.old = theta.init
logpost.old = sum(dnorm(y,theta.old,1,log=T)) + dcauchy(theta.old,log=T)	 # log posterior at the current value
for (i in 1:T) {
	theta.new = rnorm(1, theta.old, sqrt(delta2))
	logpost.new = sum(dnorm(y,theta.new,1,log=T)) + dcauchy(theta.new,log=T)	 # log posterior at the proposed value
	alpha = min(1, exp(logpost.new - logpost.old))		# acceptance probability
	u = runif(1)
	if (u < alpha) {
		accept[i] = 1
		theta.old = theta.new
		logpost.old = logpost.new	# update log likelihood only if theta changes
	} else {
		accept[i] = 0
	}
	theta.trace[i] = theta.old
}

plot(mcmc(theta.trace))		# trace plot and density plot of posterior draws of theta

mean(accept)	# check the acceptance rate

mean(theta.trace[5001:T]) 	# check the posterior mean and variance
var(theta.trace[5001:T])




### Example 2: Metropolis algorithm for logistic regression

## simulate the data
set.seed(4231)
n = 1000
x = rnorm(n)	# generate covariate x as normal
beta0.true = 0.5
beta1.true = 0.2
y.prob = 1/(1+exp(-beta0.true-beta1.true*x))
y = rbinom(n, 1, y.prob)	

## we optimize the log posterior and find the maximizer (MAP estimate)
## we use the maximizer as the starting point in RW Metropolis, and use the Hessian to get the covariance matrix

# the log posterior function, with independent Cauchy prior
logpost = function (theta, y, x) {
	-sum(log(1+exp(-theta[1]-theta[2]*x[y==1]))) - sum(log(1+exp(theta[1]+theta[2]*x[y==0]))) - log(1+theta[1]^2) - log(1+theta[2]^2) 
}
opt.logpost = optim(par=c(0,0), logpost, y=y, x=x, control=list(fnscale=-1), hessian=T)	# minimize the negative log posterior function

theta.map = opt.logpost$par		
Sigma = 2.4^2/2 * solve(-opt.logpost$hessian)		# RW covariance matrix
chol.Sigma = t(chol(Sigma))		# Cholesky of Sigma, chol.Sigma is lower triangular

# An alternative way is to use the glm() function, but this does not include the prior information
# fit1 = glm(y ~ x, family=binomial(link=logit))
# theta.mle = fit1$coef
# Sigma = 2.4^2/2 * vcov(fit1)


## Random walk Metropolis algorithm
T = 12000
burnin = 2000
accept = numeric(T)
theta.trace = matrix(0, nrow=T, ncol=2)

theta.old = theta.map
logpost.old = logpost(theta.old, y, x)		# current value of log posterior

for (i in 1:T) {
	theta.new = theta.old + chol.Sigma%*%rnorm(2)	# RW proposal from normal with variance Sigma
	logpost.new = logpost(theta.new, y, x)
	alpha = min(1, exp(logpost.new - logpost.old))
	u = runif(1)
	if (u < alpha) {
		accept[i] = 1
		theta.old = theta.new
		logpost.old = logpost.new	# update log likelihood only if theta changes
	} else {
		accept[i] = 0
	}
	theta.trace[i,] = theta.old
}

plot(mcmc(theta.trace))		# check trace plots and density plots

mean(accept)	# check acceptance rate

apply(theta.trace[(burnin+1):T,], 2, mean)	# check posterior means and variances of beta0 and beta1
apply(theta.trace[(burnin+1):T,], 2, var)




### Example 3: Gibbs sampler for Dirichlet distribution
# f(x,y,z) is proportional to x^4*y^3*z^2*(1-x-y-z) 
# theta = (x,y,z)

T = 12000
burnin = 2000
theta.trace = matrix(0, nrow = T, ncol = 3)
theta.current = rep(0.1,3)	# initialize

for (i in 1:T) {
	x.new = rbeta(1,5,2) * (1-theta.current[2]-theta.current[3])
	y.new = rbeta(1,4,2) * (1-x.new-theta.current[3])
	z.new = rbeta(1,3,2) * (1-x.new-y.new)
	theta.current = c(x.new,y.new,z.new)
	theta.trace[i,] = theta.current
}
apply(theta.trace[(burnin+1):T,], 2, mean)		# the mean of (x,y,z) should be (5/14,4/14,3/14)




### Example 4: Gibbs sampler for a normal model 

n = 1000
mu.true = 1
sigma2.true = 4
set.seed(4231)
y = rnorm(n, mu.true, sqrt(sigma2.true))	# generate the data

T = 12000	# total number of iterations in MCMC
burnin = 2000
mu.trace = numeric(T)
sigma2.trace = numeric(T)
mu.old = 0
sigma2.old = 1
ybar = mean(y)
ysum2 = sum((y-ybar)^2)		# store the mean and variance of y

# Gibbs sampler
for (i in 1:T) {
	mu.new = rnorm(1, ybar, sigma2.old/n)
	sigma2.new = 1/rgamma(1, shape=n/2, rate=(ysum2 + n*(mu.new-ybar)^2)/2)	# this way is more efficient
	mu.old = mu.new
	sigma2.old = sigma2.new
	mu.trace[i] = mu.new
	sigma2.trace[i] = sigma2.new
}

theta.trace = cbind(mu.trace,sigma2.trace)
plot(mcmc(theta.trace))	# check the trace plots and marginal density plots

apply(theta.trace[(burnin+1):T,], 2, mean)	# check posterior means and variances of beta0 and beta1
apply(theta.trace[(burnin+1):T,], 2, var)











###### 1. EM algorithm for mixture of two normals with known mixing probabilities
### The model is f(x) = p * N(x;mu1,sigma^2) + (1-p) * N(x;mu2,sigma^2)
### mu1 and mu2 are unknown. We use EM to estimate mu1 and mu2.
### p and sigma^2 are known. Below we set p = 0.5, sigma^2 = 1.

p = 0.5 
sigma2 = 1
mu1.true = -2
mu2.true = 2	# true values of mu1 and mu2 

### simulate data
set.seed(4231)
n = 1000	# sample size of observed data from mixture of two normals
z = rbinom(n, 1, p)		# generate the true labels (unobservable when fitting the data)
y = numeric(n)			# the observed data
y[z==1] = rnorm(sum(z), mean=mu1.true, sd=sqrt(sigma2))	# here z=1 comes from N(mu1,sigma^2)
y[z==0] = rnorm(n-sum(z), mean=mu2.true, sd=sqrt(sigma2))		# here z=0 comes from N(mu2,sigma^2)


### finding the MLE of (mu1,mu2) using EM algorithm
mu1.track = vector()
mu2.track = vector()	# save all iterations of mu1 and mu2 

epsilon = 1e-6		# tolerance error threshold
error = 1			# initilize the error; we use the sum of absolute errors in mu1 and mu2
counter = 0	
mu1 = -1
mu2 = 1 		# initializing mu1 and mu2; it is better to set them to be different (for identification purpose)
 
### EM algorithm
while (error > epsilon) {
	counter = counter + 1
	# E-step
	alpha1 = exp(-(y-mu1)^2/(2*sigma2)) / (exp(-(y-mu1)^2/(2*sigma2)) + exp(-(y-mu2)^2/(2*sigma2)))
	alpha2 = 1 - alpha1 
	# M-step
	mu1.new = sum(alpha1*y) / sum(alpha1)
	mu2.new = sum(alpha2*y) / sum(alpha2)	
	error = abs(mu1.new-mu1) + abs(mu2.new-mu2)		
	# absolute errors in mu1 and mu2; convergence when they stablize
	mu1 = mu1.new
	mu2 = mu2.new		# update the values of mu1 and mu2
	mu1.track[counter] = mu1
	mu2.track[counter] = mu2
	cat("iter=", counter, " mu=", mu1, " mu2=", mu2, "\n")
}

# check the results; this should converge very fast (no more than 20 steps)
mu1.track	
mu2.track
# draw a histogram to check the result
fitted.density = function(x) {p*dnorm(x,mean=mu1,sd=sqrt(sigma2))+(1-p)*dnorm(x,mean=mu2,sd=sqrt(sigma2))}
x.grid = seq(-5,5,by=0.01)
hist(y, freq=F, breaks=20, col="cyan")
abline(v=mu1, lwd=2, col="red")
abline(v=mu2, lwd=2, col="red")
lines(x.grid, fitted.density(x.grid), lwd=2, lty=2, col="red")	# superpose the fitted density

 

###### 2. EM algorithm for mixture of two normals with known mixing probabilities
### The model is f(x) = p * N(x;mu1,sigma^2) + (1-p) * N(x;mu2,sigma^2)
### p, mu1, mu2 are unknown. We use EM to estimate them.
### sigma^2 is known. Below we set sigma^2 = 1.

sigma2 = 1
p.true = 0.3
mu1.true = -2
mu2.true = 2	# true values of p, mu1 and mu2 

### simulate data
set.seed(4231)
n = 1000	# sample size of observed data from mixture of two normals
z = rbinom(n, 1, p.true)	# generate the true labels (unobservable when fitting the data)
y = numeric(n)			# the observed data
y[z==1] = rnorm(sum(z), mean=mu1.true, sd=sqrt(sigma2))	# here z=1 comes from N(mu1,sigma^2)
y[z==0] = rnorm(n-sum(z), mean=mu2.true, sd=sqrt(sigma2))		# here z=0 comes from N(mu2,sigma^2)


### finding the MLE of (mu1,mu2) using EM algorithm
p.track = vector()
mu1.track = vector()
mu2.track = vector()	# save all iterations of mu1 and mu2 

epsilon = 1e-6		# tolerance error threshold
error = 1			# initilize the error; we use the sum of absolute errors in mu1 and mu2

counter = 0	
p = 0.5 		# initializing p, mu1 and mu2
mu1 = -1
mu2 = 1
 
### EM algorithm
while (error > epsilon) {
	counter = counter + 1
	# E-step
	alpha1 = p*exp(-(y-mu1)^2/(2*sigma2)) / (p*exp(-(y-mu1)^2/(2*sigma2)) + (1-p)*exp(-(y-mu2)^2/(2*sigma2)))
	alpha2 = 1 - alpha1 
	# M-step
	p.new = mean(alpha1)
	mu1.new = sum(alpha1*y) / sum(alpha1)
	mu2.new = sum(alpha2*y) / sum(alpha2)	
	error = abs(p.new-p) + abs(mu1.new-mu1) + abs(mu2.new-mu2)		
	# absolute errors in mu1 and mu2; convergence when they stablize
	p = p.new
	mu1 = mu1.new
	mu2 = mu2.new		# update the values of mu1 and mu2
	p.track[counter] = p
	mu1.track[counter] = mu1
	mu2.track[counter] = mu2
	cat("iter=", counter, " p=", p, " mu=", mu1, " mu2=", mu2, "\n")
}

# check the results; this should converge very fast (no more than 20 steps)
p.track
mu1.track	
mu2.track 
# draw a histogram to check the result
fitted.density = function(x) {p*dnorm(x,mean=mu1,sd=sqrt(sigma2))+(1-p)*dnorm(x,mean=mu2,sd=sqrt(sigma2))}
x.grid = seq(-5,5,by=0.01)
hist(y, freq=F, breaks=20, col="cyan")
abline(v=mu1, lwd=2, col="red")
abline(v=mu2, lwd=2, col="red")
lines(x.grid, fitted.density(x.grid), lwd=2, lty=2, col="red")	# superpose the fitted density



###### 3. EM algorithm for zero-truncated Poisson
### the observed data are drawn from a Poisson but with all zeros truncated

### simulate data
set.seed(4231)
n = 1000	# sample size
lambda.true = 2		# about exp(-2) = 13.6% of the Poissom sample are zeros and they are truncated
y = numeric(n)		
counter = 1	
while (counter <= n) {
	y.new = rpois(1, lambda.true)
	if (y.new > 0) {
		y[counter] = y.new
		counter = counter + 1
	}
}

### find the MLE of lambda using EM algorithm
lambda.track = vector()
lambda = 1	# initialize lambda
epsilon = 1e-6		# tolerance error threshold
error = 1			# initilize the error; we use the absolute error in lambda
y.sum = sum(y)
counter = 0

### EM algorithm
while (error > epsilon) {
	counter = counter + 1
	lambda.new = (1-exp(-lambda)) * y.sum / n
	error = abs(lambda.new - lambda)
	lambda = lambda.new
	lambda.track[counter] = lambda
	cat(" iter = ", counter, "  lambda = ", lambda, "\n")
}

# check the MLE of lambda found by EM
lambda

### alternatively, we can maximize the log-likelihood directly using numerical methods
log.lik = function(lambda) {
	- (y.sum * log(lambda) - n*lambda - n*log(1-exp(-lambda)))
}

mle.lambda = optim(par=1, log.lik, hessian=T)
mle.lambda		# check the result and compare it with the EM fit above




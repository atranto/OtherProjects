###### Importance sampling, Self-normalized importance sampling ######

###### Example 1: Inappropriate importance sampling 
### calculate E[sin^2(X)] with X ~ Cauchy(0,1), using N(0,1) as a proposal

f1 = function(x) {
	(sin(x))^2/(pi*(1+x^2))
}
integrate(f1,-100,100)		# find the true value of E[sin^2(X)] with X ~ Cauchy(0,1)

set.seed(4231)
N = 10^5
n = seq(100, N, by = 100)
x = rcauchy(N)		# simple sampling from Cauchy
f1 = function(m, x) {
	mean((sin(x[1:m]))^2)
}
theta.est = unlist(lapply(n, f1, x = x))	# simple sampling estimates from Cauchy(0,1)

x = rnorm(N)		# importance sampling with inappropriate g(x) = N(0,1)
f2 = function(m, x) {
	y = x[1:m]
	mean((sin(y))^2 * dcauchy(y)/dnorm(y))
}
theta.est.is = unlist(lapply(n, f2, x = x))		# importance sampling estimates from N(0,1)

wt.is = dcauchy(x)/dnorm(x)

par(mfrow=c(1,2))

plot(n, theta.est, ylim=c(0.3,0.5), cex=2,
	 main="Inappropriate I.S.", xlab="sample size", ylab="estimate",
	 lwd=2, type="l", col="blue")
lines(n, theta.est.is, lwd=2, col="red")
legend(1.5*10^4, 0.5, bty="n", cex=1.5,
	 c("S.S.", "I.S."),
       lwd=rep(2,2),
	 col=c("blue","red"))

hist(log(wt.is)/log(10), breaks=100, cex=2,
	main="log_10(IS weights)", xlab="log_10(weights)",
	ylab="density", col="cyan", freq=F)
	
	
	
###### Example 2: Importance sampling for \int_0^1 h(x) dx, where h(x) = 4 * sqrt(1 - x^2)

### (i) Simple sampling estimate based on Uniform(0,1)

n = 10^4
set.seed(4231)
x = runif(n)
ss.est = mean(4*sqrt(1-x^2))	# simple sampling estimate
var.ss.est = var(4*sqrt(1-x^2))		# estimated asymptotic variance for simple sampling


### (ii) Importance sampling estimate based on an inappropriate proposal g(x) = 2*x

n = 10^4
set.seed(4231)
y = sqrt(runif(n))		# sampling from the density g(x)=2*x, 0<x<1.
is.est1 = mean(2*sqrt(1-y^2)/y)		# importance sampling estimate
var.is.est1 = var(2*sqrt(1-y^2)/y)	
# estimated asymptotic variance, which is unstable from simulation to simulation (because it is infinity)


### (iii) Importance sampling estimate based on an appropriate proposal g(x) = (4-2*x)/3

n = 10^4
set.seed(4231)
# first use inversion method to draw from g(x) = (4-2*x)/3, 0<x<1
# the inversion method gives y = 2-sqrt(4-3*u) for u ~ Uniform(0,1)
u = runif(n)		
y = 2-sqrt(4-3*u)
is.est2 = mean(6*sqrt(1-y^2)/(2-y))		# importance sampling estimate
var.is.est2 = var(6*sqrt(1-y^2)/(2-y))	# estimated asymptotic variance

### Draw plots
h = function(x) {
	4 * sqrt(1-x^2)
}	
x = seq(0,1,by=0.01)
plot(x, h(x), main="h(x) = 4*sqrt(1-x^2)", xlab="x", ylab="h(x)",
	 lwd=2, type="l")
lines(x, 2*x, lwd=2, lty=2, col="red")	 
lines(x, (4-2*x)/3, lwd=2, col="blue")		



###### Example 3: Rare event estimation
### calculate P(X>4) for X ~ N(0,1)
### the true value is pnorm(-4)

set.seed(4231)
N = 10^5
n = seq(100, N, by = 100)		# sample sizes from 100 to 10^5, with 100 increment
x1 = rnorm(N)		# simple sampling from N(0,1)
f1 = function(m, x) {
	y = x[1:m]
	return(list(mean.ss=mean(y>4), sd.ss=sd(y>4)/pnorm(-4)))	# sd.ss is the relative s.d.
}
ss.est = lapply(n, f1, x = x1)
mean.ss = vector()		# store simple sampling estimates
sd.ss = vector()		# store relative s.d.'s
for (i in 1:length(n)) {
	mean.ss[i] = ss.est[[i]]$mean.ss
	sd.ss[i] = ss.est[[i]]$sd.ss
}


x2 = rcauchy(N, 4, 1)		# importance sampling using the proposal Cauchy(4,1)
f2 = function(m, x) {
	y = x[1:m]
	y = y[y>4]
	wt = dnorm(y)/dcauchy(y, 4, 1)
	mean.is = sum(wt)/m
	sd.is = sqrt(sum(wt^2*dcauchy(y,4,1))/m - mean.is^2) / pnorm(-4)
	return(list(mean.is=mean.is, sd.is=sd.is))
}
is.est = lapply(n, f2, x = x2)
mean.is = vector()			# store importance sampling estimates
sd.is = vector()			# store relative s.d.'s
for (i in 1:length(n)) {
	mean.is[i] = is.est[[i]]$mean.is
	sd.is[i] = is.est[[i]]$sd.is
}

### draw plots
par(mfrow=c(1,2))
plot(n, sd.ss, cex=2,
	 main="Simple Sampling for Rare Event", xlab="sample size", ylab="relative s.d.",
	 lwd=2, type="l", col="blue")
plot(n, sd.is, cex=2,
	 main="Importance Sampling for Rare Event", xlab="sample size", ylab="relative s.d.",
	 lwd=2, type="l", col="red")
	

	
###### Example 4: Self-normalized importance sampling 
### Estimate E[sin(X)], for X with pdf f(x) = exp(-x^2/2)/Z on -1<x<1. True value = 0.
### We use the proposal pdf g(x) = 1/2, for -1<x<1, i.e. Uniform(-1,1).

set.seed(4231)
n = 10^4
x = 2*runif(n)-1	# generate Uniform(-1,1) r.v.'s
phi = sin(x)		# phi(x)
w = exp(-x^2/2)*2	# unnormalized weights
theta.est = sum(phi * w)/sum(w)		# SIS estimate
var.est = sum((phi-theta.est)^2*w^2)/(sum(w))^2		# estimated variance of SIS estimator

# we can repeat this process for N=1000 times and plot the empirical distribution of theta.est1
set.seed(4231)
n = 10^4
N = 10^3
theta.est = numeric(N)
for (i in 1:N) {
	x = 2*runif(n)-1	# generate Uniform(-1,1) r.v.'s
	phi = sin(x)		# phi(x)
	w = exp(-x^2/2)*2	# unnormalized weights
	theta.est[i] = sum(phi * w)/sum(w)		# SIS estimate
}
var(theta.est) * n		# empirically estimated asymptotic variance
hist(theta.est, breaks=25, freq=F, col="cyan", main="Distribution of SIS Estimator", xlab="SIS estimate", ylab="density")
lines(density(theta.est), lwd=2, lty=2, col="red")



	
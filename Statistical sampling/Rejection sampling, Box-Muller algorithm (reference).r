###### Rejection sampling, Box-Muller algorithm ######

###### Example 1: Rejection sampling from Beta(2,4).

n = 10^5	# we draw n samples from Beta(2,4) 
const = 256/27
x = numeric(n)	# record the x samples
S = numeric(n)	# record the number of iterations needed for each acceptance

set.seed(2018)
for (i in 1:n) {
	y = runif(1)
	u = runif(1)
	counter = 1		# count the number of iterations for each new x
	while (u > const*y*(1-y)^3) {
		y = runif(1)	# propose another y
		u = runif(1)
		counter = counter + 1
	}
	x[i] = y
	S[i] = counter
}

mean(S)	# the average number of iterations to simulate one single x
summary(x)
hist(x)	# plot the histogram of the x sample

### plot for the rejection sampling for Example 1

n = 10^3
y.full = vector()	# record all proposed y
u.full = vector()	# record all proposed u
S = numeric(n)	# record the number of iterations needed for each acceptance

set.seed(2018)
for (i in 1:n) {
	y = runif(1)
	u = runif(1)
	y.full = c(y.full, y)
	u.full = c(u.full, u)
	counter = 1		# count the number of iterations for each new x
	while (u > const*y*(1-y)^3) {
		y = runif(1)	# propose another y
		u = runif(1)
		y.full = c(y.full, y)
		u.full = c(u.full, u)
		counter = counter + 1
	}
	S[i] = counter
}

M = 135/64
data.full = cbind(y.full, u.full * M)	# combined 2-d vector (y,u)
ind.accept = cumsum(S)	# accepted indexes
ind.reject = setdiff(1:sum(S), cumsum(S))		# rejected indexes

# plot the rejection sampler
curve(dbeta(x,2,4),xlim=c(0,1),ylim=c(0,M),lwd=3,col="gold",
	xlab="x",ylab="density")
abline(h=M, lwd=3, col="blue")
abline(h=0, lwd=3, col="blue")
points(data.full[ind.accept,],lwd=2,col="green",pch=24)
points(data.full[ind.reject,],lwd=2,col="red",pch=25)


###### Example 2: Rejection sampling from Gamma(1.5,1)

n = 10^5		# we draw n samples
const = 2*exp(1)/3 
x = numeric(n)	# record the x samples
S = numeric(n)	# record the number of iterations needed for each acceptance

set.seed(2018)
for (i in 1:n) {
   u1 = runif(1)
   u2 = runif(1)
   y = -log(u1)*3/2
   counter = 1
   while (u2 > ((const*y)^(1/2)*exp(-y/3))) {
       u1 = runif(1)
	   u2 = runif(1)
       y = -log(u1)*3/2		# propose another y
       counter = counter + 1
   }
   x[i] = y
   S[i] = counter
}

mean(S)	# the average number of iterations to simulate one single x
summary(x)
hist(x)	# plot the histogram of the x sample


###### Example 3: Rejection sampling without normalizing constant

n = 10^5	# we draw n samples
x = numeric(n)	# record the x samples
S = numeric(n)	# record the number of iterations needed for each acceptance

set.seed(2018)
for (i in 1:n) {
	y = -log(runif(1))*3
	u = runif(1)
	counter = 1		# count the number of iterations for each new x
	while (u > exp(-sqrt(y^2+1)/3+y/3)) {
		y = -log(runif(1))*3	# propose another y
		u = runif(1)
		counter = counter + 1
	}
	x[i] = y
	S[i] = counter
}

mean(S)	# the average number of iterations to simulate one single x
summary(x)
hist(x)	# plot the histogram of the x sample


###### Box-Muller Algorithm v1 

set.seed(2018)
n = 10^3
u1 = runif(n)
R = sqrt(-2*log(u1))
theta = runif(n, min=0, max=2*pi)
X = R * cos(theta)
Y = R * sin(theta)

### draw 2d contour plots for the bivariate density
library(RColorBrewer)
library(MASS)
k.col = 10
my.cols = rev(brewer.pal(k.col, "RdYlBu"))
Z = kde2d(X, Y, n=50)	# 2d kernel density estimation
xy.data = cbind(X,Y)
plot(xy.data, xlab="X", ylab="Y", pch=19, cex=.6)
contour(Z, drawlabels=F, nlevels=k.col, col=my.cols, lwd=2, add=T)
abline(h=mean(X), v=mean(Y), lwd=2)


###### Box-Muller Algorithm v2

set.seed(2018)
n = 10^3
counter = 0
X = numeric(n)
Y = numeric(n)

while (counter < n) {
	u1 = runif(1, min=-1, max=1)
	u2 = runif(1, min=-1, max=1)
	s = u1^2 + u2^2
	if (s <= 1) {
		r.new = sqrt(-2*log(s)/s)
		x = r.new * u1
		y = r.new * u2
		counter = counter + 1
		X[counter] = x
		Y[counter] = y
	}
}

### draw 2d contour plots for the bivariate density
library(RColorBrewer)
library(MASS)
k.col = 10
my.cols = rev(brewer.pal(k.col, "RdYlBu"))
Z = kde2d(X, Y, n=50)	# 2d kernel density estimation
xy.data = cbind(X,Y)
plot(xy.data, xlab="X", ylab="Y", pch=19, cex=.6)
contour(Z, drawlabels=F, nlevels=k.col, col=my.cols, lwd=2, add=T)
abline(h=mean(X), v=mean(Y), lwd=2)


###### Generate general multivariate normals
### We generate 10-dimensional normal, with mean=1:10, var=1:10, all equal correlation = 0.5

d = 10
n = 10^3
mu = 1:10
cor.mat = matrix(0.5, nrow = d, ncol = d)
diag(cor.mat) = 1
D = diag(sqrt(1:10))
Sigma = D %*% cor.mat %*% D

### Method 1: in one command mvrnorm() from MASS
library(MASS)
X = mvrnorm(n, mu, Sigma)	# generate n samples of d-dimensional normal
colMeans(X)	# check sample means
cov(x)		# check sample variances

### Method 2: using Cholesky decomposition
chol.x = chol(Sigma)	# chol.x is upper triangular, by R default
Z = matrix(rnorm(n*d), nrow=n, ncol=d)
X = Z %*% chol.x + rep(mu, each = n)	# be careful with the matrix operation!
colMeans(X) # check sample means
cov(x)		# check sample variances

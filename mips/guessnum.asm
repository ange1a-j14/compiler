# This program generates a random number in the range [0, 100)
# and prompts the user to guess the number
# by printing whether the guess is too high, too low,
# out of range, or correct.
#
# @author Angela Jia
# @version 5/7/22
.data
msg:
.asciiz "Guess a number between [0, 100)\n"
msg2:
.asciiz "Not in range. Guess again\n"
high:
.asciiz "Too high. Guess again\n"
low:
.asciiz "Too low. Guess again\n"
correct:
.asciiz "Correct!\n"
.text 0x00400000
.globl main
main:
	li $a0, 77	# id of random num generator
	li $a1, 100	# upper bound of rand range
	li $v0, 42
	syscall
	move $t0, $a0	# put rand number in $t0
	j prompt

prompt:
	la $a0, msg
	li $v0, 4
	syscall		# print msg
	j read

read:
	li $v0, 5
	syscall		# read num
	blt $v0, 0, invalid
	bge $v0, $a1, invalid
	j start

invalid:
	la $a0, msg2
	li $v0, 4
	syscall
	j read

start:			# $v0 contains user num
	blt $v0, $t0, guessl
	bgt $v0, $t0, guessh
	la $a0, correct
	li $v0, 4
	syscall 	# print "correct!"
	li $v0, 10
	syscall		# end

guessl:
	la $a0, low
	li $v0, 4
	syscall
	j read

guessh:
	la $a0, high
	li $v0, 4
	syscall
	j read

# This program prints whether a user-inputted number
# is even or odd.
#
# @author Angela Jia
# @version 5/7/22
.data
msg:
.asciiz "Input a number\n"
evenmsg:
.asciiz "Even\n"
oddmsg:
.asciiz "Odd\n"
.text 0x00400000
.globl main
main:
	la $a0, msg
	li $v0, 4
	syscall		# print msg
	li $v0, 5
	syscall
	move $t0, $v0	# store num in $t0
	li $t1, 2	# store 2 in $t1
	div $t0, $t1
	mfhi $t2	# store remainder in $t2
	beq $t2, 0, even
	la $a0, oddmsg
	j after
even:
	la $a0, evenmsg
	j after
after:
	li $v0, 4
	syscall
	li $v0, 10
	syscall

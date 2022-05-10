# This program prompts the user for a low limit, high limit,
# and increment value. Then it prints values within the range
# (inclusive), incremented from the lower limit by the step value.
#
# @author Angela Jia
# @version 5/7/22
.data
msg:
.asciiz "Enter a lower and upper limit for a range\n"
msg2:
.asciiz "Enter a step\n"
.text 0x00400000
.globl main
main:
	la $a0, msg
	li $v0, 4
	syscall		# print msg
	li $v0, 5
	syscall
	move $t0, $v0	# store first int in $t0
	li $v0, 5
	syscall
	blt $v0, $t0, switch	# branch if 2nd < 1st
	move $t1, $v0		# if 2nd > 1st, put 2nd in $t1
	la $a0, msg2
	li $v0, 4
	syscall			# print msg2
	li $v0, 5
	syscall
	move $t2, $v0 		# store step in $t2
	move $a0, $t0		# $a0 stores what will be printed
	j after			# $t0 has smaller, $t1 has larger

switch:
	move $t1, $t0		# put first int in $t1
	move $t0, $v1		# put second int in $t0
	move $a0, $t0		# $a0 stores what will be printed
	j after			# $t0 has smaller, $t1 has larger

after:
	blt $t1, $a0, endloop
	li $v0, 1
	syscall
	addu $a0, $a0, $t2 	# add step to printed value
	j after

endloop:
	li $v0, 10
	syscall

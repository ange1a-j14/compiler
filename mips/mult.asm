# This program prompts the user for two numbers and
# prints the product of them.
#
# @author Angela Jia
# @version 5/7/22
.data
msg:
.asciiz "Input 2 integers\n"
.text 0x00400000
.globl main
main:
	la $a0, msg
	li $v0, 4
	syscall 	# print message

	li $v0, 5
	syscall		# read first int
	move $t0, $v0	# store first int in $t0
	li $v0, 5
	syscall		# read second int
	move $t1, $v0	# store second int in $t1

	mult $t0, $t1
	mflo $a0 	# store product in $a0
	li $v0, 1
	syscall 	# print product
	li $v0, 10
	syscall		# exit

	# @author Angela Jia
	# @version Thu May 12 18:06:50 PDT 2022
	.data
	nl: .asciiz "\n"
	varx: .word 0
	vary: .word 0
	varcount: .word 0
	.text
	.globl main
main:
	li $v0 2
	sw $v0 varx      # set variable value
	la $t0 varx
	lw $v0 ($t0)        # load variable
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	addu $v0 $t0 $v0
	sw $v0 vary      # set variable value
	la $t0 varx
	lw $v0 ($t0)        # load variable
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	la $t0 vary
	lw $v0 ($t0)        # load variable
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	addu $v0 $t0 $v0
	sw $v0 varx      # set variable value
	la $t0 varx
	lw $v0 ($t0)        # load variable
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	la $t0 vary
	lw $v0 ($t0)        # load variable
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	mult $v0 $t0
	mflo $v0
	move $a0 $v0
	li $v0 1
	syscall
	la $a0 nl
	li $v0 4
	syscall     # print expression and move to new line
	la $t0 varx
	lw $v0 ($t0)        # load variable
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	la $t0 vary
	lw $v0 ($t0)        # load variable
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	ble $t0 $v0 endif1
	la $t0 varx
	lw $v0 ($t0)        # load variable
	move $a0 $v0
	li $v0 1
	syscall
	la $a0 nl
	li $v0 4
	syscall     # print expression and move to new line
	la $t0 vary
	lw $v0 ($t0)        # load variable
	move $a0 $v0
	li $v0 1
	syscall
	la $a0 nl
	li $v0 4
	syscall     # print expression and move to new line
endif1:
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	bne $t0 $v0 endif2
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	beq $t0 $v0 endif3
	li $v0 3
	move $a0 $v0
	li $v0 1
	syscall
	la $a0 nl
	li $v0 4
	syscall     # print expression and move to new line
endif3:
	li $v0 14
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	bgt $t0 $v0 endif4
	li $v0 4
	move $a0 $v0
	li $v0 1
	syscall
	la $a0 nl
	li $v0 4
	syscall     # print expression and move to new line
endif4:
endif2:
	li $v0 15
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	li $v0 14
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	ble $t0 $v0 endif5
	li $v0 5
	move $a0 $v0
	li $v0 1
	syscall
	la $a0 nl
	li $v0 4
	syscall     # print expression and move to new line
endif5:
	li $v0 1
	sw $v0 varcount      # set variable value
loop6:
	la $t0 varcount
	lw $v0 ($t0)        # load variable
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	li $v0 15
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	bgt $t0 $v0 endw7
	la $t0 varcount
	lw $v0 ($t0)        # load variable
	move $a0 $v0
	li $v0 1
	syscall
	la $a0 nl
	li $v0 4
	syscall     # print expression and move to new line
	la $t0 varcount
	lw $v0 ($t0)        # load variable
	subu $sp $sp 4
	sw $v0 ($sp)		# push from reg to stack
	li $v0 1
	lw $t0 ($sp)
	addu $sp $sp 4		# pop from stack to reg
	addu $v0 $t0 $v0
	sw $v0 varcount      # set variable value
	j loop6
endw7:
	li $v0 10
	syscall     # terminate

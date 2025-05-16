NAME= SudokuSolver
SRCS= $(addprefix src/, \
	main.c \
	)

OBJS= $(SRCS:.c=.o)

INCLUDE_DIR=include

all: $(NAME)

$(NAME): $(OBJS)
	gcc $(OBJS) -o $(NAME) -I$(INCLUDE_DIR)

%.o: %.c
	gcc -c $< -o $@ -I$(INCLUDE_DIR)

clean:
	rm $(OBJS)

fclean: clean
	rm $(NAME)

re: fclean all
NAME= GameOfLife
SRCS= $(addprefix src/, \
	main.c \
	)

OBJS= $(SRCS:.c=.o)

all: $(NAME)

$(NAME): $(OBJS)
	gcc $(OBJS) -o $(NAME)

%.o: %.c
	gcc -c $< -o $@

clean:
	rm $(OBJS)

fclean: clean
	rm $(NAME)

re: fclean all
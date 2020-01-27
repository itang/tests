package main

import (
	"fmt"

	"github.com/hibiken/asynq"
)

func main() {
	r := &asynq.RedisClientOpt{
		Addr: "localhost:6379",
	}

	bg := asynq.NewBackground(r, &asynq.Config{
		Concurrency: 10,
		Queues: map[string]uint{
			"critical": 6,
			"default":  3,
			"low":      1,
		},
	})

	bg.Run(&Handler{})

	//c := make(<-chan string)
	//<-c
}

type Handler struct {
}

func (handler *Handler) ProcessTask(task *asynq.Task) error {
	fmt.Printf("receive %v\n", task)
	return nil
}

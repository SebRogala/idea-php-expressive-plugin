<?php

namespace {{ ModuleName }}\Service;

use Doctrine\ORM\EntityManager;
use Xsv\Doctrine\Hydrator\Hydrator;

class Delete{{ ResourceName }}DoctrineService
{
    /** @var EntityManager */
    protected $entityManager;

    /**
     * Delete{{ ResourceName }}DoctrineService constructor.
     * @param EntityManager $entityManager
     */
    public function __construct(EntityManager $entityManager)
    {
        $this->entityManager = $entityManager;
    }

    public function delete($id)
    {
        $found{{ ResourceName }} = $this->entityManager->getRepository('{{ ModuleName }}\Entity\{{ ResourceName }}')->findById($id);

        if(empty($found{{ ResourceName }})) {
            return 0;
        }

        $this->entityManager->remove($found{{ ResourceName }});
        $this->entityManager->flush();

        return 1;
    }
}

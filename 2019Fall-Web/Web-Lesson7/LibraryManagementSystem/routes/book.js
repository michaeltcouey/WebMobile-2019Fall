var express = require('express');
var router = express.Router();
var Book = require('../models/Book.js');

/* GET ALL BOOKS */
router.get('/', function (req, res, next) {
  Book.find(function (err, products) {
    if (err) return next(err);
    res.json(products);
  });
});

/* GET SINGLE BOOK BY ID */
router.get('/:id', function (req, res, next) {
  Book.findById(req.params.id, function (err, post) {
    if (err) return next(err);
    res.json(post);
  });
});

/* SAVE BOOK */
router.post('/', function (req, res, next) {
  Book.create(req.body, function (err, post) {
    if (err) return next(err);
    res.json(post);
  });
});

/* UPDATE BOOK */
router.put('/:id', function (req, res, next) {
  Book.findByIdAndUpdate(req.params.id,
    req.body,
    {new: true},
    (err, book) => {
      // Handle any possible database errors
      if (err) return res.status(500).send(err);
    }
  );
});
/* DELETE BOOK */
router.delete('/:id', function (req, res, next) {
  Book.findOneAndDelete({_id: req.params.id}, (err) => {
    if (err) return next(err);
    res.json({ message: 'Successfully deleted' });
  });
});

module.exports = router;
